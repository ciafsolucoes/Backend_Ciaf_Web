package br.com.ciaf.admin.controllers.jasper_controll;

import br.com.ciaf.admin.config.multitenant.TenantContext;
import br.com.ciaf.admin.services.jasper_services.JasperMultiTenantService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relatorios")
@CrossOrigin(origins = "*")
public class ReportController {

    private final JasperMultiTenantService2 reportService;

    private final TenantContext tenantContext;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    public ReportController(JasperMultiTenantService2 reportService, TenantContext tenantContext) {
        this.reportService = reportService;
        this.tenantContext = tenantContext;
    }

    @PostMapping(value = "/{nome}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<byte[]> gerarRelatorioPdf(
    public ResponseEntity<String> gerarRelatorioPdf(
            @PathVariable String nome,
            @RequestParam(defaultValue = "true") boolean reescreverQuery,
            @RequestBody(required = false) Map<String, Object> parametros,
            HttpServletRequest request) {
        try {
            logger.info("Recebida requisição para relatório: {}", nome);

            // Usar parâmetros ou criar mapa vazio
            Map<String, Object> params = parametros != null ? parametros : new HashMap<>();
            Object emitenteId = params.get("emitente_id");
            Object basicParameter = params.get("basic_parameter");
            String basicParameterValue = basicParameter == null ? null : String.valueOf(basicParameter);
            String basicParameterTrimmed = basicParameterValue == null ? null : basicParameterValue.trim();
            logger.info(
                    "Parâmetros recebidos para relatório '{}': emitente_id={} (tipo={}), basic_parameter='{}' (len={}, trim='{}')",
                    nome,
                    emitenteId,
                    emitenteId == null ? "null" : emitenteId.getClass().getSimpleName(),
                    basicParameterValue,
                    basicParameterValue == null ? null : basicParameterValue.length(),
                    basicParameterTrimmed
            );

               if (params.get("emitente_id") != null){
                   Path testDirectory = Paths.get(
                           "/var/www/ciafonline.com.br/reports_" + params.get("emitente_id")
                   );
                   if (Files.notExists(testDirectory)) {
                       Files.createDirectories(testDirectory);
                   }
                   byte[] pdfBytes = reportService.generateReportWithQueryRewriting(nome, params);
                   nome = buildFileName(nome, parametros);
                   File file = new File("/var/www/ciafonline.com.br/reports_" + params.get("emitente_id") + "/" + nome+ ".pdf");
                   FileOutputStream fis = new FileOutputStream(file);
                   fis.write(pdfBytes);
                   fis.flush();
                   fis.close();
               }

            // Configurar resposta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentLength(pdfBytes.length);
            headers.setContentDisposition(
                    ContentDisposition.builder("inline")
                            .filename(nome + "_" + TenantContext.getCurrentTenant() + ".pdf")
                            .build()
            );
            logger.info("Relatório {} gerado com sucesso para tenant {}",
                    nome, TenantContext.getCurrentTenant());

//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            return new ResponseEntity<>("Gerado com sucesso!", headers, HttpStatus.OK);
//            return ResponseEntity.ok().headers(headers).body("Gerado com sucesso!");

        } catch (Exception e) {
            logger.error("Erro ao gerar relatório {}: {}", nome, e.getMessage(), e);

            String errorMessage = "Erro ao gerar relatório: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(errorMessage);
//                    .body(errorMessage.getBytes(StandardCharsets.UTF_8));
        }
    }
    private String buildFileName(String reportName, Map<String, Object> params) {
        String baseName = reportName;

        // Exemplo: para o relatório "Orcamento", acrescentar "-<id>"
        if ("Orcamento".equalsIgnoreCase(reportName)) {
            Object idObj = params.get("my_budget_id");          // ou "orcamento_id", conforme o JSON
            if (idObj != null) {
                baseName = reportName + "-" + idObj;  // Orcamento-123
            }
        }else if ("Venda".equalsIgnoreCase(reportName)){
            Object idVenda = params.get("my_sale_id");          // ou "orcamento_id", conforme o JSON
            if (idVenda != null) {
                baseName = reportName + "-" + idVenda;  // Orcamento-123
            }
        } else if ("ord_serv".equals(reportName)) {
            Object idOS = params.get("os_id");
            if (idOS != null){
                baseName = reportName + "-" +idOS;
            }
        }else if ("PersonsBirth".equals(reportName)) {
            Object mes = params.get("basic_parameter");
            if (mes != null){
                baseName = reportName + "-" + mes;
            }
        }
        return baseName;
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<String>> listarRelatoriosDisponiveis() {
        try {
            List<String> relatorios = reportService.getAvailableReports();
            return ResponseEntity.ok(relatorios);
        } catch (Exception e) {
            logger.error("Erro ao listar relatórios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/limpar-cache")
    public ResponseEntity<String> limparCache() {
        try {
            reportService.clearReportCache();
            return ResponseEntity.ok("Cache de relatórios limpo com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao limpar cache: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao limpar cache: " + e.getMessage());
        }
    }
}
