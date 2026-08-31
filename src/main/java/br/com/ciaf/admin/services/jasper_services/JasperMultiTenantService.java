package br.com.ciaf.admin.services.jasper_services;

import br.com.ciaf.admin.config.multitenant.TenantContext;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.pdf.JRPdfExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class JasperMultiTenantService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TenantContext tenantContext;

    @Value("${jasper.reports.path:reports}")
    private String reportsPath;

    private static final Logger logger = LoggerFactory.getLogger(JasperMultiTenantService.class);

    // Cache de relatórios
    private final Map<String, JasperReport> reportCache = new ConcurrentHashMap<>();

    /**
     * Método principal para gerar relatórios PDF
     */
    public byte[] generateReport(String reportName, Map<String, Object> parameters)
            throws Exception {

        String tenantSchema = tenantContext.getCurrentTenant();
        logger.info("Gerando relatório '{}' para tenant: {}", reportName, tenantSchema);

        // 1. Garantir que as views de ponte estejam criadas
        ensureDatabaseAliasExists(tenantSchema);

        // 2. Carregar relatório
        JasperReport report = loadCompiledReport(reportName);

        // 3. Obter conexão e configurar
        try (Connection connection = dataSource.getConnection()) {

            // Mudar para o database do tenant
            try (Statement stmt = connection.createStatement()) {
                String useSQL = "USE `" + escapeDatabaseName(tenantSchema) + "`";
                logger.debug("Executando: {}", useSQL);
                stmt.execute(useSQL);
            }

            // 4. Preencher relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report, parameters, connection
            );

            // 5. Exportar para PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            logger.error("Erro ao gerar relatório {}: {}", reportName, e.getMessage(), e);
            throw e;
        }
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

    /**
     * Cria/atualiza o database "ciaf_id" com views para o tenant atual
     */
    private void createOrUpdateDatabaseAlias(Connection connection, String tenantSchema)
            throws SQLException {

        try (Statement stmt = connection.createStatement()) {
            // 1. Criar database "ciaf_id" se não existir
            String createDbSQL = "CREATE DATABASE IF NOT EXISTS `ciaf_id`";
            stmt.execute(createDbSQL);
            logger.debug("Database ciaf_id verificado/criado");

            // 2. Mudar para "ciaf_id" para criar views
            stmt.execute("USE `ciaf_id`");

            // 3. Obter todas as tabelas do tenant atual
            List<String> tenantTables = getTablesFromDatabase(connection, tenantSchema);
            logger.info("Encontradas {} tabelas no tenant {}", tenantTables.size(), tenantSchema);

            // 4. Para cada tabela, criar view
            for (String table : tenantTables) {
                try {
                    // Drop view se existir
                    String dropViewSQL = "DROP VIEW IF EXISTS `" + table + "`";
                    stmt.execute(dropViewSQL);

                    // Criar nova view apontando para o tenant
                    String createViewSQL = String.format(
                            "CREATE VIEW `%s` AS SELECT * FROM `%s`.`%s`",
                            escapeIdentifier(table),
                            escapeDatabaseName(tenantSchema),
                            escapeIdentifier(table)
                    );

                    stmt.execute(createViewSQL);
                    logger.debug("View criada: {}.{}", "ciaf_id", table);

                } catch (SQLException e) {
                    logger.warn("Não foi possível criar view para tabela {}: {}",
                            table, e.getMessage());
                }
            }

            logger.info("Views criadas/atualizadas no database ciaf_id para tenant {}", tenantSchema);

        } catch (SQLException e) {
            logger.error("Erro ao criar alias: {}", e.getMessage(), e);
            throw e;
        }
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
     * Método alternativo: reescreve queries em tempo de execução
     * Útil se não quiser criar views
     */
    public byte[] generateReportWithQueryRewriting(String reportName,
                                                   Map<String, Object> parameters)
            throws Exception {

        String tenantSchema = tenantContext.getCurrentTenant();
        logger.info("Gerando relatório com reescrita de query: {} para {}",
                reportName, tenantSchema);

        // 1. Carregar relatório
        JasperReport report = loadCompiledReport(reportName);

        // 2. Obter query original e reescrever
        String originalQuery = report.getQuery().getText();
        String rewrittenQuery = rewriteQueryForTenant(originalQuery, tenantSchema);

        logger.debug("Query original: {}", originalQuery);
        logger.debug("Query reescrita: {}", rewrittenQuery);

        // 3. Criar relatório modificado
        JasperReport modifiedReport = createReportWithNewQuery(report, rewrittenQuery);

        // 4. Obter conexão para o tenant
        try (Connection connection = dataSource.getConnection()) {

            // Usar database do tenant
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("USE `" + escapeDatabaseName(tenantSchema) + "`");
            }

            // 5. Preencher relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    modifiedReport, parameters, connection
            );

            // 6. Exportar
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    /**
     * Reescreve query SQL substituindo "ciaf_id" pelo schema do tenant
     */
    private String rewriteQueryForTenant(String originalQuery, String tenantSchema) {
        // Substituir "ciaf_id." por "tenantSchema."
        // Usando regex para garantir que só substitua prefixos de tabela
        String escapedSchema = escapeDatabaseName(tenantSchema);

        // Padrão: ciaf_id. seguido por nome de tabela
        String pattern = "ciaf_id\\.(\\w+)";
        String replacement = escapedSchema + ".$1";

        return originalQuery.replaceAll(pattern, replacement);
    }

    /**
     * Cria um novo relatório com uma query diferente
     */
    private JasperReport createReportWithNewQuery(JasperReport originalReport,
                                                  String newQuery) throws JRException {

        // Clonar o relatório para não modificar o original
        JasperReport clonedReport = (JasperReport) originalReport.getCompileData();

        // Criar nova query
        JRDesignQuery designQuery = new JRDesignQuery();
        designQuery.setText(newQuery);

        // Obter e modificar o dataset
        JRDesignDataset dataset = (JRDesignDataset) clonedReport.getMainDataset();
        dataset.setQuery(designQuery);

        return clonedReport;
    }

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
     * Escapa identificadores para MySQL
     */
    private String escapeIdentifier(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificador não pode ser vazio");
        }

        return "`" + identifier.replace("`", "``") + "`";
    }

    /**
     * Carrega relatório do cache ou disco
     */
    private JasperReport loadCompiledReport(String reportName) throws JRException {
        return reportCache.computeIfAbsent(reportName, key -> {
            try {
                // Tentar várias localizações possíveis
                InputStream is = null;

                // 1. Classpath raiz
                is = getClass().getResourceAsStream("/" + reportName + ".jasper");

                // 2. Diretório reports no classpath
                if (is == null) {
                    is = getClass().getResourceAsStream("/reports/" + reportName + ".jasper");
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