package br.com.ciaf.admin.controllers;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;

@RestController
@RequestMapping("/print")
public class PrintController {


    @GetMapping
    public String print() {
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty("net.sf.jasperreports.default.locale", "pt_BR");

        try {
            JasperReportCompiler();
            //Faça a leitura do Arquivo
            String xml = ImpressaoUtil.leArquivo("/var/www/ciafonline.com.br/xmls/NFe_156.xml");
            System.out.println(xml);
            //Aqui está pegando o Layout Padrão
            Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(xml);

            //Faz a impressão em pdf File passando o caminho do Arquivo
           //ImpressaoService.impressaoHtml(impressao, "/var/www/ciafonline.com.br/danfes/teste-nfe.html");
            impressao.setJasper(ImpressaoUtil.carregaJasperResources("/var/www/ciafonline.com.br/danfes/danfeBase.jasper"));
            //Para trocar a Logo
//            impressao.getParametros().put("Logo", TesteImpressaoModificado.class.getResourceAsStream("/logo-java.png"));

            //Faz a impressão
            byte[] bytes = ImpressaoService.impressaoPdfByte(impressao);
        } catch (Exception e) {
            //Trate seus erros aqui
            e.printStackTrace();
        }
        return "Hello World!";
    }
    @GetMapping("/pdf")
    public String printPdf(){        try {
            JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty("net.sf.jasperreports.default.locale", "pt_BR");

            //Faça a leitura do Arquivo
            String xml = ImpressaoUtil.leArquivo("/var/www/ciafonline.com.br/xmls/NFe_156.xml");

            //Aqui está pegando o Layout Padrão
            Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(xml);

            //Faz a impressão em pdf File passando o caminho do Arquivo
            ImpressaoService.impressaoPdfArquivo(impressao, "/var/www/ciafonline.com.br/danfes/myDanfeLoka.pdf");

        } catch (Exception e) {
            //Trate seus erros aqui
            e.printStackTrace();
        }
        return "Teste Impressão PDF 8081";
    }

    public void JasperReportCompiler() {
        try {
            // Path to the .jrxml file
//            String jrxmlFile = "/home/gilvan/ciaf/danfe.jrxml";
            String jrxmlFile = "/home/gilvan/ciaf/id-api2/src/main/resources/jasper/danfe.jrxml";

            // Compile into a .jasper file
            JasperCompileManager.compileReportToFile(jrxmlFile, "/var/www/ciafonline.com.br/danfes/danfeBase.jasper");

            System.out.println("Compilation successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
