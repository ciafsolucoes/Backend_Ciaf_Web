package br.com.ciaf.admin.services.jasper_services;

import br.com.ciaf.admin.config.multitenant.TenantContext;
import io.micrometer.core.instrument.util.IOUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class JasperMultiTenantService2 {
    private static final Locale REPORT_LOCALE_PT_BR = new Locale("pt", "BR");
    private static final TimeZone REPORT_TIME_ZONE_SP = TimeZone.getTimeZone("America/Sao_Paulo");

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TenantContext tenantContext;

    @Value("${jasper.reports.path:src/main/resources}")
    private String reportsPath;

    private static final Logger logger = LoggerFactory.getLogger(JasperMultiTenantService2.class);

    private static final Set<String> TABLES_IN_DEFAULT_SCHEMA =
            new HashSet<>(Arrays.asList(
                    "emitente", // adicione outras se precisar
                    "cfop"
            ));

    // Cache de relatórios
    private final Map<String, JasperReport> reportCache = new ConcurrentHashMap<>();
    // ... outros métodos ...
    /**
     * Escapa nome de database para MySQL
     */
    private String escapeDatabaseName(String dbName) {
        if (dbName == null || dbName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome de database não pode ser vazio");
        }

        // Remove caracteres perigosos e escapa backticks
        return "`" + dbName.replace("`", "``") + "`";
    }

    /**
     * Reescreve query SQL substituindo "ciaf_id" pelo schema do tenant
     */
//    private String rewriteQueryForTenant(String originalQuery, String tenantSchema) {
//        // Substituir "ciaf_id." por "tenantSchema."
//        // Usando regex para garantir que só substitua prefixos de tabela
//        String escapedSchema = escapeDatabaseName(tenantSchema);
//
//        // Padrão: ciaf_id. seguido por nome de tabela
//        String pattern = "ciaf_id\\.(\\w+)";
//        String replacement = escapedSchema + ".$1";
//
//        return originalQuery.replaceAll(pattern, replacement);
//    }

    private String rewriteQueryForTenant(String originalQuery, String tenantSchema) {
        // Schemas escapados (com crases) para usar na query final
        String escapedTenantSchema = escapeDatabaseName(tenantSchema);   // ex: `emitente_1`
        String escapedDefaultSchema = escapeDatabaseName("ciaf_id");     // ex: `ciaf_id`

        // Encontra ocorrências de ciaf_id.<tabela>
        Pattern pattern = Pattern.compile("ciaf_id\\.(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(originalQuery);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String table = matcher.group(1); // nome da tabela depois do ponto

            if (TABLES_IN_DEFAULT_SCHEMA.contains(table.toLowerCase())) {
                // Mantém no schema padrão
                // Resultado: `ciaf_id`.emitente
                String replacement = escapedDefaultSchema + "." + table;
                matcher.appendReplacement(sb, replacement);
            } else {
                // Vai para o schema do tenant
                // Resultado: `emitente_1`.persons, etc.
                String replacement = escapedTenantSchema + "." + table;
                matcher.appendReplacement(sb, replacement);
            }
        }

        matcher.appendTail(sb);
        String newQuery = sb.toString();
//        logger.debug("Query original: {}", originalQuery);
//        logger.debug("Query reescrita: {}", newQuery);

        return newQuery;
    }


    private JasperReport loadCompiledReport(String reportName) throws JRException {
        return reportCache.computeIfAbsent(reportName, key -> {
            try {
                // Tentar várias localizações possíveis
                InputStream is = null;

                // 1. Classpath raiz
                is = getClass().getResourceAsStream("/" + reportName + ".jasper");

                // 2. Diretório reports no classpath
                if (is == null) {
                    is = getClass().getResourceAsStream("/jasper/" + reportName + ".jasper");
                }

                // 3. Filesystem
                if (is == null) {
                    File reportFile = new File(reportsPath, reportName + ".jasper");
                    if (reportFile.exists()) {
                        is = new FileInputStream(reportFile);
                    }
                }

                // 4. Caminho absoluto
                if (is == null) {
                    File reportFile = new File(reportName + ".jasper");
                    if (reportFile.exists()) {
                        is = new FileInputStream(reportFile);
                    }
                }

                if (is == null) {
                    throw new FileNotFoundException(
                            "Relatório não encontrado: " + reportName +
                                    ". Procurou em: classpath:/, classpath:/reports/, " +
                                    reportsPath + ", diretório atual"
                    );
                }

                JasperReport report = (JasperReport) JRLoader.loadObject(is);
                logger.info("Relatório '{}' carregado com sucesso", reportName);
                return report;

            } catch (Exception e) {
                logger.error("Erro ao carregar relatório {}: {}", reportName, e.getMessage(), e);
                throw new RuntimeException("Falha ao carregar relatório: " + reportName, e);
            }
        });
    }
    /**
     * Escapa identificadores para MySQL
     */
    private String escapeIdentifier(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificador não pode ser vazio");
        }

        return "`" + identifier.replace("`", "``") + "`";
    }
    /**
     * Obtém lista de tabelas de um database
     */
    private List<String> getTablesFromDatabase(Connection connection, String databaseName)
            throws SQLException {

        List<String> tables = new ArrayList<>();

        // Primeiro, voltar para o database original para consultar information_schema
        String currentDb = connection.getCatalog();

        // Consultar information_schema para obter tabelas do database do tenant
        String sql = String.format(
                "SELECT TABLE_NAME FROM information_schema.tables " +
                        "WHERE TABLE_SCHEMA = '%s' AND TABLE_TYPE = 'BASE TABLE' " +
                        "ORDER BY TABLE_NAME",
                databaseName
        );

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        }

        return tables;
    }

    /**
     * Cria/atualiza o database "ciaf_id" com views para o tenant atual
     */
    private void createOrUpdateDatabaseAlias(Connection connection, String tenantSchema)
            throws SQLException {

//        try (Statement stmt = connection.createStatement()) {
//            // 1. Criar database "ciaf_id" se não existir
//            String createDbSQL = "CREATE DATABASE IF NOT EXISTS `ciaf_id`";
//            stmt.execute(createDbSQL);
//            logger.debug("Database ciaf_id verificado/criado");
//
//            // 2. Mudar para "ciaf_id" para criar views
//            stmt.execute("USE `ciaf_id`");
//
//            // 3. Obter todas as tabelas do tenant atual
//            List<String> tenantTables = getTablesFromDatabase(connection, tenantSchema);
//            logger.info("Encontradas {} tabelas no tenant {}", tenantTables.size(), tenantSchema);

            // 4. Para cada tabela, criar view
//            for (String table : tenantTables) {
//                try {
                    // Drop view se existir
//                    String dropViewSQL = "DROP VIEW IF EXISTS `" + table + "`";
//                    stmt.execute(dropViewSQL);

                    // Criar nova view apontando para o tenant
//                    String createViewSQL = String.format(
//                            "CREATE VIEW %s AS SELECT * FROM %s.%s",
//                            escapeIdentifier(table),
//                            escapeDatabaseName(tenantSchema),
//                            escapeIdentifier(table)
//                    );
//
//                    stmt.execute(createViewSQL);
//                    logger.debug("View criada: {}.{}", "ciaf_id", table);
//
//                } catch (SQLException e) {
//                    logger.warn("Não foi possível criar view para tabela {}: {}",
//                            table, e.getMessage());
//                }
//            }

//            logger.info("Views criadas/atualizadas no database ciaf_id para tenant {}", tenantSchema);
//
//        } catch (SQLException e) {
//            logger.error("Erro ao criar alias: {}", e.getMessage(), e);
//            throw e;
//        }
        System.out.println("into create database");
    }

    /**
     * Garante que o database "ciaf_id" exista com as views
     */
    private void ensureDatabaseAliasExists(String tenantSchema) {
        try (Connection connection = dataSource.getConnection()) {
            createOrUpdateDatabaseAlias(connection, tenantSchema);
        } catch (SQLException e) {
            logger.error("Erro ao criar alias do database: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao preparar ambiente de relatórios", e);
        }
    }

    private Map<String, Object> withDefaultReportLocale(Map<String, Object> parameters) {
        Map<String, Object> reportParams = new HashMap<>();
        if (parameters != null) {
            reportParams.putAll(parameters);
        }
        reportParams.put(JRParameter.REPORT_LOCALE, REPORT_LOCALE_PT_BR);
        reportParams.put(JRParameter.REPORT_TIME_ZONE, REPORT_TIME_ZONE_SP);
        return reportParams;
    }
    /**
     * Método principal para gerar relatórios PDF
     */
    public byte[] generateReport(String reportName, Map<String, Object> parameters)
            throws Exception {

//        String tenantSchema = TenantContext.getCurrentTenantSchema();
        String tenantSchema = TenantContext.getCurrentTenant();
        logger.info("Gerando relatório '{}' para tenant: {}", reportName, tenantSchema);

        // 1. Garantir que as views de ponte estejam criadas
//        ensureDatabaseAliasExists(tenantSchema);

        // 2. Carregar relatório
        JasperReport report = loadCompiledReport(reportName);
        Map<String, Object> reportParams = withDefaultReportLocale(parameters);

        // 3. Obter conexão e configurar
        try (Connection connection = dataSource.getConnection()) {

            // Mudar para o database do tenant
            try (Statement stmt = connection.createStatement()) {
                String useSQL = "USE " + escapeDatabaseName(tenantSchema);
                logger.debug("Executando: {}", useSQL);
                stmt.execute(useSQL);
            }

            // 4. Preencher relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report, reportParams, connection
            );

            // 5. Exportar para PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            logger.error("Erro ao gerar relatório {}: {}", reportName, e.getMessage(), e);
            throw e;
        }
    }



    public byte[] generateReportWithQueryRewriting(String reportName,
                                                   Map<String, Object> parameters)
            throws Exception {
        String tenantSchema = TenantContext.getCurrentTenant();
        logger.info("Gerando relatório com reescrita de query: {} para {}",
                reportName, tenantSchema);
        Object emitenteId = parameters == null ? null : parameters.get("emitente_id");
        Object basicParameter = parameters == null ? null : parameters.get("basic_parameter");
        String basicParameterValue = basicParameter == null ? null : String.valueOf(basicParameter);
        logger.info(
                "Parâmetros brutos no serviço para '{}': emitente_id={} (tipo={}), basic_parameter='{}' (len={}, trim='{}')",
                reportName,
                emitenteId,
                emitenteId == null ? "null" : emitenteId.getClass().getSimpleName(),
                basicParameterValue,
                basicParameterValue == null ? null : basicParameterValue.length(),
                basicParameterValue == null ? null : basicParameterValue.trim()
        );
        JasperReport report = loadCompiledReport(reportName);

        String originalQuery = queryOriginal(reportName, report);
        String rewrittenQuery = rewriteQueryForTenant(originalQuery, tenantSchema);
//        System.out.println(rewrittenQuery+ " <= originalQuery -> " + tenantSchema);
        // sem fallback, só para debug:
//        logger.info("Query reescrita para '{}': {}", reportName, rewrittenQuery);
        return generateReportFromJRXMLWithNewQuery(reportName, rewrittenQuery, parameters, tenantSchema);
    }


    /**
     * Abordagem 1: Compilar JRXML com query modificada
     */
    private byte[] generateReportFromJRXMLWithNewQuery(String reportName,
                                                       String newQuery,
                                                       Map<String, Object> parameters,
                                                       String tenantSchema)
            throws Exception {

        // 1. Carregar JRXML
        InputStream jrxmlStream = loadJRXMLStream(reportName);
        if (jrxmlStream == null) {
            throw new FileNotFoundException("JRXML não encontrado para: " + reportName);
        }

        // 2. Ler conteúdo
        String jrxmlContent = IOUtils.toString(jrxmlStream, StandardCharsets.UTF_8);

        // 3. Substituir query no JRXML
        jrxmlContent = replaceQueryInJRXML(jrxmlContent, newQuery);

        // 4. Compilar em memória
        JasperReport compiledReport = JasperCompileManager.compileReport(
                new ByteArrayInputStream(jrxmlContent.getBytes(StandardCharsets.UTF_8))
        );

        Map<String, Object> coercedParams = coerceParametersToReportTypes(compiledReport, parameters);
        coercedParams = withDefaultReportLocale(coercedParams);
        Object coercedEmitenteId = coercedParams.get("emitente_id");
        Object coercedBasicParameter = coercedParams.get("basic_parameter");
        String coercedBasicParameterValue = coercedBasicParameter == null ? null : String.valueOf(coercedBasicParameter);
        logger.info(
                "Parâmetros coerced para '{}': emitente_id={} (tipo={}), basic_parameter='{}' (len={}, trim='{}')",
                reportName,
                coercedEmitenteId,
                coercedEmitenteId == null ? "null" : coercedEmitenteId.getClass().getSimpleName(),
                coercedBasicParameterValue,
                coercedBasicParameterValue == null ? null : coercedBasicParameterValue.length(),
                coercedBasicParameterValue == null ? null : coercedBasicParameterValue.trim()
        );

        // 5. Executar relatório
        try (Connection connection = getConnectionForTenant(tenantSchema)) {
            logMissingQueryParameters(reportName, newQuery, coercedParams);
            logQueryDataDiagnostics(reportName, newQuery, coercedParams, connection);
            if ("venda".equalsIgnoreCase(reportName)) {
                logVendaDiagnostics(connection, coercedParams, tenantSchema);
            }
            logger.info("Parâmetros finais '{}': {}", reportName, coercedParams);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    compiledReport, coercedParams
                    , connection
            );
            // Diagnostics: pages + some core parameters
            int pages = jasperPrint.getPages() == null ? 0 : jasperPrint.getPages().size();
//            logger.info("Report '{}' filled. pages={}", reportName, pages);
//            logger.info("Relatório '{}' para tenant '{}' gerado com {} páginas",
//                    reportName, tenantSchema, jasperPrint.getPages().size());

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    /**
     * Carrega stream do JRXML
     */
    /**
     * Consulta original do relatório. Orçamento: vem do .jrxml (o .jasper pré-compilado está desatualizado e não traz
     * os campos/joins novos); os demais continuam usando a consulta do .jasper.
     */
    private String queryOriginal(String reportName, JasperReport report) {
        String query = report.getQuery().getText();
        if ("Orcamento".equals(reportName)) {
            try (InputStream js = loadJRXMLStream(reportName)) {
                if (js != null) {
                    String xml = IOUtils.toString(js, StandardCharsets.UTF_8);
                    Matcher qm = Pattern.compile("<query[^>]*>\\s*<!\\[CDATA\\[(.*?)\\]\\]>\\s*</query>", Pattern.DOTALL).matcher(xml);
                    if (qm.find()) query = qm.group(1);
                }
            } catch (Exception e) {
                logger.warn("Não foi possível ler a consulta do JRXML de '{}': {}", reportName, e.getMessage());
            }
        }
        return query;
    }

    private InputStream loadJRXMLStream(String reportName) {
        // Tentar várias localizações
        String[] locations = {
                "/reports/" + reportName + ".jrxml",
                "/" + reportName + ".jrxml",
                reportsPath + "/" + reportName + ".jrxml",
                reportsPath + "/reports/" + reportName + ".jrxml"
        };

        for (String location : locations) {
            try {
                // Classpath
                InputStream is = getClass().getResourceAsStream(location);
                if (is != null) return is;

                // Filesystem
                File file = new File(location);
                if (file.exists()) {
                    return new FileInputStream(file);
                }
            } catch (Exception e) {
                // Continuar tentando
            }
        }

        return null;
    }
    private Map<String, Object> coerceParametersToReportTypes(JasperReport report, Map<String, Object> input) {
        Map<String, Object> params = new HashMap<>();
        if (input != null) {
            params.putAll(input);
        }

        for (JRParameter p : report.getParameters()) {
            String name = p.getName();

            // Ignore built-in Jasper parameters
            if (p.isSystemDefined() || name == null) {
                continue;
            }

            if (!params.containsKey(name)) {
                continue;
            }

            Object value = params.get(name);
            if (value == null) {
                continue;
            }

            Class<?> expected = p.getValueClass();
            try {
                params.put(name, coerceValue(value, expected));
            } catch (Exception ex) {
                // Keep original value, but log for diagnostics
                logger.warn("Não foi possível converter parâmetro '{}' de {} para {} (valor={}). Mantendo valor original.",
                        name,
                        value.getClass().getName(),
                        expected != null ? expected.getName() : "null",
                        value);
            }
        }

        return params;
    }



    private Object coerceValue(Object value, Class<?> expected) {
        if (expected == null || value == null) return value;
        if (expected.isInstance(value)) return value;

        if (expected == String.class) {
            return String.valueOf(value);
        }

        // Most important case: report expects Double but JSON gave Integer/Long/etc.
        if (expected == Double.class) {
            if (value instanceof Number n) return n.doubleValue();
            if (value instanceof String s) return Double.valueOf(s.trim().replace(",", "."));
        }

        if (expected == Integer.class) {
            if (value instanceof Number n) return n.intValue();
            if (value instanceof String s) return Integer.valueOf(s.trim());
        }

        if (expected == Long.class) {
            if (value instanceof Number n) return n.longValue();
            if (value instanceof String s) return Long.valueOf(s.trim());
        }

        if (expected == Float.class) {
            if (value instanceof Number n) return n.floatValue();
            if (value instanceof String s) return Float.valueOf(s.trim().replace(",", "."));
        }

        if (expected == java.math.BigDecimal.class) {
            if (value instanceof java.math.BigDecimal bd) return bd;
            if (value instanceof Number n) return java.math.BigDecimal.valueOf(n.doubleValue());
            if (value instanceof String s) return new java.math.BigDecimal(s.trim().replace(",", "."));
        }

        if (expected == java.util.Date.class) {
            if (value instanceof java.util.Date d) return d;
            if (value instanceof Number n) return new java.util.Date(n.longValue());
            if (value instanceof String s) return parseDateString(s);
        }

        // Fallback: keep as-is
        return value;
    }

    private java.util.Date parseDateString(String raw) {
        if (raw == null) return null;
        String value = raw.trim();
        if (value.isEmpty()) return null;

        // yyyy-MM-dd (ex.: 2026-04-01)
        try {
            LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException ignored) {
        }

        // yyyy-MM-ddTHH:mm:ss or yyyy-MM-ddTHH:mm:ss.SSS
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException ignored) {
        }

        // yyyy-MM-dd HH:mm:ss
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(value, fmt);
            return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException ignored) {
        }

        // dd/MM/yyyy
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(value, fmt);
            return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException ignored) {
        }

        throw new IllegalArgumentException("Formato de data inválido: " + raw);
    }


    /**
     * Substitui a query principal do JRXML por uma nova query.
     * Cuida de:
     * - Tags <queryString ...> ou <query ...>
     * - Conteúdo multi-linha
     * - Presença de $P{...} na query (escapa para o replaceFirst)
     */
    private String replaceQueryInJRXML(String jrxmlContent, String newQuery) {
        // 1) Escapar caracteres especiais do replacement para o replaceFirst:
        //    - "\" precisa ser "\\"
        //    - "$" precisa ser "\$" para não ser interpretado como referência de grupo
        String escapedQuery = newQuery
                .replace("\\", "\\\\")
                .replace("$", "\\$");

        // 2) Tentar primeiro <queryString ...>
        String patternQueryString =
                "(?s)<queryString[^>]*>\\s*<!\\[CDATA\\[.*?\\]\\]>\\s*</queryString>";
        String replacementQueryString =
                "<queryString><![CDATA[" + escapedQuery + "]]></queryString>";

        String updated = jrxmlContent.replaceFirst(patternQueryString, replacementQueryString);

        // 3) Se nada foi substituído, tentar <query ...>
        if (updated.equals(jrxmlContent)) {
            String patternQuery =
                    "(?s)<query[^>]*>\\s*<!\\[CDATA\\[.*?\\]\\]>\\s*</query>";
            String replacementQuery =
                    "<query><![CDATA[" + escapedQuery + "]]></query>";

            updated = jrxmlContent.replaceFirst(patternQuery, replacementQuery);
        }

        // (Opcional) Se ainda assim nada foi substituído, logar para debug
        if (updated.equals(jrxmlContent)) {
            logger.warn("Nenhuma <queryString> ou <query> foi encontrada para substituição no JRXML");
        }

        return updated;
    }


    /**
     * Abordagem 2: Modificar dataset via reflexão (para relatórios compilados)
     */
    private byte[] generateReportWithModifiedDataset(JasperReport report,
                                                     String newQuery,
                                                     Map<String, Object> parameters,
                                                     String tenantSchema)
            throws Exception {

        // Criar novo dataset com a query modificada
        JRDataset dataset = createDatasetWithQuery(report.getMainDataset(), newQuery);

        // Criar parâmetros incluindo o dataset
        Map<String, Object> reportParams = new HashMap<>();
        if (parameters != null) {
            reportParams.putAll(parameters);
        }

        // Adicionar dataset modificado como parâmetro
        reportParams.put("REPORT_DATASET", dataset);
        reportParams = withDefaultReportLocale(reportParams);

        // Usar conexão do tenant
        try (Connection connection = getConnectionForTenant(tenantSchema)) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report, reportParams, connection
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    /**
     * Cria um novo dataset baseado em um existente, mas com query diferente
     */
    private JRDataset createDatasetWithQuery(JRDataset originalDataset, String newQuery) {
        try {
            // Usar JRDesignDataset para criar dataset modificado
            JRDesignDataset newDataset = new JRDesignDataset(false);

            // Copiar propriedades do dataset original
            newDataset.setName(originalDataset.getName());

            // Configurar nova query
            JRDesignQuery designQuery = new JRDesignQuery();
            designQuery.setText(newQuery);
            newDataset.setQuery(designQuery);

            // Copiar campos do dataset original
            JRField[] fields = originalDataset.getFields();
            if (fields != null) {
                for (JRField field : fields) {
                    // Converter JRField para JRDesignField
                    JRDesignField designField = new JRDesignField();
                    designField.setName(field.getName());
                    designField.setValueClass(field.getValueClass());
                    designField.setDescription(field.getDescription());

                    // Adicionar campo ao novo dataset
                    newDataset.addField(designField);
                }
            }

            return newDataset;

        } catch (JRException e) {
            throw new RuntimeException("Erro ao criar dataset modificado", e);
        }
    }

    /**
     * Abordagem 3: Usar Subreport com query dinâmica (RECOMENDADA)
     */
    public byte[] generateReportUsingDynamicSubreport(String reportName,
                                                      Map<String, Object> parameters,
                                                      String tenantSchema)
            throws Exception {

        // 1. Carregar relatório principal (sem query)
//        JasperReport masterReport = loadCompiledReport("report_master");
        JasperReport masterReport = loadCompiledReport(reportName);

        // 2. Criar subreport dinamicamente
        JasperReport dynamicSubreport = createDynamicSubreport(reportName, tenantSchema);

        // 3. Preparar parâmetros
        Map<String, Object> reportParams = new HashMap<>();
        if (parameters != null) {
            reportParams.putAll(parameters);
        }
        reportParams.put("SUBREPORT", dynamicSubreport);
        reportParams = withDefaultReportLocale(reportParams);

        // 4. Executar
        try (Connection connection = getConnectionForTenant(tenantSchema)) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    masterReport, reportParams, connection
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    /**
     * Cria um subreport dinâmico
     */
    private JasperReport createDynamicSubreport(String reportName, String tenantSchema)
            throws JRException, FileNotFoundException {

        // 1. Carregar template de subreport
        InputStream subreportTemplate = getClass().getResourceAsStream(
                "/reports/templates/dynamic_subreport.jrxml"
        );

        if (subreportTemplate == null) {
            throw new FileNotFoundException("Template de subreport não encontrado");
        }

        // 2. Ler template
        String templateContent = IOUtils.toString(subreportTemplate, StandardCharsets.UTF_8);

        // 3. Obter query específica para este relatório
        String specificQuery = getQueryForReport(reportName);
        String rewrittenQuery = rewriteQueryForTenant(specificQuery, tenantSchema);

        // 4. Substituir query no template
        templateContent = templateContent.replace("${DYNAMIC_QUERY}", rewrittenQuery);

        // 5. Compilar
        return JasperCompileManager.compileReport(
                new ByteArrayInputStream(templateContent.getBytes(StandardCharsets.UTF_8))
        );
    }

    /**
     * Obtém a query SQL para um relatório específico
     */
    private String getQueryForReport(String reportName) {
        // Mapeamento de relatórios para queries
        Map<String, String> reportQueries = new HashMap<>();

        // Adicionar todas as queries dos seus 25 relatórios
        reportQueries.put("orcamento",
                "SELECT ciaf_id.budgetsitems.prod_vun_com, ... WHERE ciaf_id.budgets.id = $P{my_budget_id}");

        reportQueries.put("vendas",
                "SELECT ciaf_id.sales.* FROM ciaf_id.sales WHERE ...");

        // ... adicione todas as queries dos seus relatórios

        String query = reportQueries.get(reportName);
        if (query == null) {
            throw new IllegalArgumentException("Query não encontrada para relatório: " + reportName);
        }

        return query;
    }

    /**
     * Abordagem 4: Mais Simples - Usar DataSource em memória
     */
    public byte[] generateReportUsingDataSource(String reportName,
                                                Map<String, Object> parameters,
                                                String tenantSchema)
            throws Exception {

        // 1. Carregar relatório
        JasperReport report = loadCompiledReport(reportName);

        // 2. Obter query e reescrever
        String originalQuery = queryOriginal(reportName, report);
        String rewrittenQuery = rewriteQueryForTenant(originalQuery, tenantSchema);

        // 3. Executar query diretamente no banco
        List<Map<String, Object>> data = executeQueryAndGetData(rewrittenQuery, parameters, tenantSchema);

        // 4. Converter para JRDataSource
        JRDataSource dataSource = createJRDataSourceFromList(data);
        Map<String, Object> reportParams = withDefaultReportLocale(parameters);

        // 5. Preencher relatório com DataSource
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                report, reportParams, dataSource
        );

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Executa query e retorna dados como lista de maps
     */
    private List<Map<String, Object>> executeQueryAndGetData(String query,
                                                             Map<String, Object> parameters,
                                                             String tenantSchema)
            throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection connection = getConnectionForTenant(tenantSchema);
             PreparedStatement stmt = prepareStatementWithParameters(connection, query, parameters);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }
        }

        return result;
    }

    /**
     * Prepara statement com parâmetros
     */
    private PreparedStatement prepareStatementWithParameters(Connection connection,
                                                             String query,
                                                             Map<String, Object> parameters)
            throws SQLException {

        // Substituir parâmetros Jasper ($P{...}) por ? para JDBC
        String jdbcQuery = query.replaceAll("\\$P\\{[^}]+\\}", "?");

        PreparedStatement stmt = connection.prepareStatement(jdbcQuery);

        // Extrair valores na ordem de ocorrência no SQL
        List<String> paramNamesInOrder = extractParameterNamesInOrder(query);
        int paramIndex = 1;
        for (String paramName : paramNamesInOrder) {
            Object value = parameters == null ? null : parameters.get(paramName);
            stmt.setObject(paramIndex++, value);
        }

        return stmt;
    }

    private List<String> extractParameterNamesInOrder(String query) {
        List<String> names = new ArrayList<>();
        if (query == null || query.isBlank()) {
            return names;
        }
        Pattern p = Pattern.compile("\\$P\\{([^}]+)}");
        Matcher m = p.matcher(query);
        while (m.find()) {
            names.add(m.group(1));
        }
        return names;
    }

    private void logMissingQueryParameters(String reportName, String query, Map<String, Object> parameters) {
        List<String> required = extractParameterNamesInOrder(query);
        if (required.isEmpty()) {
            return;
        }

        Set<String> missing = new LinkedHashSet<>();
        for (String key : required) {
            Object value = parameters == null ? null : parameters.get(key);
            if (value == null) {
                missing.add(key);
                continue;
            }
            if (value instanceof String s && s.trim().isEmpty()) {
                missing.add(key);
            }
        }

        if (!missing.isEmpty()) {
            logger.warn("Relatório '{}' com parâmetros obrigatórios ausentes/vazios: {}", reportName, missing);
        }
    }

    private void logQueryDataDiagnostics(String reportName,
                                         String query,
                                         Map<String, Object> parameters,
                                         Connection connection) {
        try {
            String countSql = "SELECT COUNT(1) AS total_rows FROM (" + query + ") AS _q";
            try (PreparedStatement stmt = prepareStatementWithParameters(connection, countSql, parameters);
                 ResultSet rs = stmt.executeQuery()) {
                long total = 0L;
                if (rs.next()) {
                    total = rs.getLong("total_rows");
                }
                logger.info("Diagnóstico relatório '{}': query retornou {} linha(s)", reportName, total);
                if (total == 0L) {
                    logger.warn("Relatório '{}' sem dados para os parâmetros informados. O PDF pode sair em branco.", reportName);
                }
            }
        } catch (Exception e) {
            logger.warn("Falha ao executar diagnóstico de linhas do relatório '{}': {}", reportName, e.getMessage());
        }
    }

    private void logVendaDiagnostics(Connection connection,
                                     Map<String, Object> parameters,
                                     String tenantSchema) {
        Object saleIdObj = parameters == null ? null : parameters.get("my_sale_id");
        if (saleIdObj == null) {
            logger.warn("Diagnóstico Venda: parâmetro 'my_sale_id' ausente.");
            return;
        }

        Long saleId;
        try {
            if (saleIdObj instanceof Number n) {
                saleId = n.longValue();
            } else {
                saleId = Long.valueOf(String.valueOf(saleIdObj).trim());
            }
        } catch (Exception ex) {
            logger.warn("Diagnóstico Venda: 'my_sale_id' inválido: {}", saleIdObj);
            return;
        }

        try {
            boolean saleExists = exists(connection,
                    "SELECT 1 FROM `" + tenantSchema + "`.vendas v WHERE v.id = ? LIMIT 1",
                    saleId);
            long itemsCount = count(connection,
                    "SELECT COUNT(1) FROM `" + tenantSchema + "`.vendasitems vi WHERE vi.venda_id = ?",
                    saleId);
            long emitenteCount = count(connection,
                    "SELECT COUNT(1) FROM `" + tenantSchema + "`.vendas v JOIN `ciaf_id`.emitente e ON e.id = v.emitente_id WHERE v.id = ?",
                    saleId);
            long productsJoined = count(connection,
                    "SELECT COUNT(1) FROM `" + tenantSchema + "`.vendasitems vi JOIN `" + tenantSchema + "`.products p ON p.id = vi.product_id WHERE vi.venda_id = ?",
                    saleId);

            logger.info("Diagnóstico Venda (tenant='{}', my_sale_id={}): saleExists={}, items={}, emitenteJoin={}, productsJoin={}",
                    tenantSchema, saleId, saleExists, itemsCount, emitenteCount, productsJoined);

            if (!saleExists) {
                logger.warn("Diagnóstico Venda: venda id={} não encontrada na tabela vendas.", saleId);
            }
            if (itemsCount == 0) {
                logger.warn("Diagnóstico Venda: venda id={} sem itens em vendasitems.", saleId);
            }
            if (emitenteCount == 0) {
                logger.warn("Diagnóstico Venda: venda id={} sem emitente correspondente (join com ciaf_id.emitente).", saleId);
            }
            if (productsJoined == 0 && itemsCount > 0) {
                logger.warn("Diagnóstico Venda: venda id={} com itens sem correspondência em products.", saleId);
            }
        } catch (Exception e) {
            logger.warn("Falha no diagnóstico específico de Venda (sale_id={}): {}", saleId, e.getMessage());
        }
    }

    private boolean exists(Connection connection, String sql, Long id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private long count(Connection connection, String sql, Long id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }
        }
    }

    /**
     * Cria JRDataSource a partir de lista de maps
     */
    private JRDataSource createJRDataSourceFromList(List<Map<String, Object>> data) {
        return new JRDataSource() {
            private int index = -1;
            private List<Map<String, Object>> rows = data;

            @Override
            public boolean next() throws JRException {
                return ++index < rows.size();
            }

            @Override
            public Object getFieldValue(JRField field) throws JRException {
                return rows.get(index).get(field.getName());
            }
        };
    }

    /**
     * Método principal SIMPLIFICADO (RECOMENDADO)
     * Usa a abordagem das views que você já implementou
     */
    public byte[] generateReportSimple(String reportName, Map<String, Object> parameters)
            throws Exception {

        String tenantSchema = tenantContext.getCurrentTenant();

        // 1. Garantir que as views existam
        createViewsForTenant(tenantSchema);

        // 2. Carregar relatório (que ainda referencia ciaf_id.*)
        JasperReport report = loadCompiledReport(reportName);
        Map<String, Object> reportParams = withDefaultReportLocale(parameters);

        // 3. Usar conexão que já está no database do tenant
        //    As views em ciaf_id.* vão redirecionar para as tabelas reais
        try (Connection connection = getConnectionForTenant(tenantSchema)) {

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report, reportParams, connection
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
    private List<String> getTablesFromTenant(Connection connection, String tenantSchema)
            throws SQLException {

        List<String> tables = new ArrayList<>();

        String sql = String.format(
                "SELECT TABLE_NAME FROM information_schema.tables " +
                        "WHERE TABLE_SCHEMA = '%s' AND TABLE_TYPE = 'BASE TABLE'",
                tenantSchema
        );

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        }

        return tables;
    }

    /**
     * Cria views para um tenant específico
     */
    private void createViewsForTenant(String tenantSchema) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            // Criar database ciaf_id se não existir
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS `ciaf_id`");
            }

            // Obter todas as tabelas do tenant
            List<String> tables = getTablesFromTenant(connection, tenantSchema);

            // Para cada tabela, criar view em ciaf_id
            for (String table : tables) {
                try (Statement stmt = connection.createStatement()) {
                    // Criar view
                    String sql = String.format(
                            "CREATE OR REPLACE VIEW `ciaf_id`.`%s` AS " +
                                    "SELECT * FROM `%s`.`%s`",
                            table, tenantSchema, table
                    );
                    stmt.execute(sql);
                } catch (SQLException e) {
                    logger.warn("Não foi possível criar view para {}: {}", table, e.getMessage());
                }
            }
        }
    }

    /**
     * Obtém conexão configurada para o tenant
     */
    private Connection getConnectionForTenant(String tenantSchema) throws SQLException {
        Connection connection = dataSource.getConnection();

        try (Statement stmt = connection.createStatement()) {
            String useSQL = "USE " + escapeDatabaseName(tenantSchema);
            // Usar database do tenant
            stmt.execute(useSQL);
        }

        return connection;
    }
    /**
     * Limpa cache de relatórios
     */
    public void clearReportCache() {
        reportCache.clear();
        logger.info("Cache de relatórios limpo");
    }

    /**
     * Lista relatórios disponíveis
     */
    public List<String> getAvailableReports() {
        List<String> reports = new ArrayList<>();

        // Procurar em várias localizações
        String[] locations = {
                reportsPath,
                "src/main/resources/jasper",
                "reports"
        };

        for (String location : locations) {
            File dir = new File(location);
            if (dir.exists() && dir.isDirectory()) {
                File[] jasperFiles = dir.listFiles((d, name) ->
                        name.toLowerCase().endsWith(".jasper"));

                if (jasperFiles != null) {
                    for (File file : jasperFiles) {
                        String name = file.getName().replace(".jasper", "");
                        if (!reports.contains(name)) {
                            reports.add(name);
                        }
                    }
                }
            }
        }

        logger.info("Encontrados {} relatórios disponíveis", reports.size());
        return reports;
    }
}
