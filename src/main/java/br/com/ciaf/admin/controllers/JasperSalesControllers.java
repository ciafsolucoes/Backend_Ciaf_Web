package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.services.JasperService;
import br.com.ciaf.admin.services.VendasServices;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/sales-reports"})
public class JasperSalesControllers {
    private static final String JASPER_TO_PAY_PDFS = "/var/www/ciafonline.com.br/sales/";
    //    private static final String JASPER_TO_PAY_PDFS = "/var/www/html/sales/";
    private final JasperService service;
    private final VendasServices vendasServices;

    public JasperSalesControllers(JasperService service, VendasServices vendasServices) {
        this.service = service;
        this.vendasServices = vendasServices;
    }

    @CrossOrigin
    @GetMapping(path = {"/{emitente_id}/{seller}/{data1}/{data2}"})
    public String geralReportsPerPeriod(
            @PathVariable Long emitente_id,
            @PathVariable String seller,
            @PathVariable String data1,
            @PathVariable String data2) throws IOException {
        List<Date> list = (List<Date>) service.covertDate(data1, data2);
        boolean check = checkSeller(emitente_id, seller);
        boolean check2 = checkInterval(emitente_id, list.get(0), list.get(1));
        String path;
        if (check && check2) {
            path = salesBySellerByPeriod(seller, list.get(0), list.get(1), emitente_id);
        } else {
            path = "No Sale";
        }
        return path;
    }

    @CrossOrigin
    @GetMapping(path = {"/commission/{emitente_id}/{seller}/{data1}/{data2}"})
    public String geralReportsPerCommission(
            @PathVariable Long emitente_id,
            @PathVariable String seller,
            @PathVariable String data1,
            @PathVariable String data2) throws IOException {
        List<Date> list = (List<Date>) service.covertDate(data1, data2);
        boolean check = checkSeller(emitente_id, seller);
        boolean check2 = checkInterval(emitente_id, list.get(0), list.get(1));
        String path;
        if (check && check2) {
            path = sellerByPeriodCommission(seller, list.get(0), list.get(1), emitente_id);
        } else {
            path = "No Sale";
        }
        return path;
    }

    @CrossOrigin
    @GetMapping(path = {"/{seller}/{emitente_id}"})
    public String salesBySeller(
            @PathVariable String seller,
            @PathVariable Long emitente_id
    ) {
        boolean check = checkSeller(emitente_id, seller);
        String document = "VendaPorVendedor";
        String path;
        if (check) {
            path = gerarSellerPdf(JASPER_TO_PAY_PDFS, document, seller, emitente_id);
        } else {
            path = "No Sale";
        }
        return path;
    }

    private boolean checkSeller(Long emitente_id, String seller) {
        List<Vendas> vendas = vendasServices.findBySeller(emitente_id, seller);
        return !vendas.isEmpty();
    }
    private boolean checkInterval(Long emitente_id, Date data1, Date data2){
        LocalDateTime newData1 = data1.toInstant()
                .atZone(ZoneId.systemDefault()) // Or any specific ZoneId
                .toLocalDateTime();
        LocalDateTime newData2 = data2.toInstant()
                .atZone(ZoneId.systemDefault()) // Or any specific ZoneId
                .toLocalDateTime();
        List<Vendas> vendas = vendasServices.findIntervalData(emitente_id, newData1, newData2);
        System.out.println(vendas.size() + " size of sales list");
        return !vendas.isEmpty();
    }

    public String salesBySellerByPeriod(String seller, Date data1, Date data2, Long emitente_id) throws IOException {
        String document = "VendaPorVendedorDatas";
        service.addParams("seller", seller);
        service.addParams("baseDate", data1);
        service.addParams("baseDate2", data2);
        service.addParams("emitente_id", emitente_id);
        return getPathPdf(JASPER_TO_PAY_PDFS, document, service);
    }

    public String sellerByPeriodCommission(String seller, Date data1, Date data2, Long emitente_id) throws IOException {
        String document = "VendaCommissionVendedor";
        service.addParams("seller", seller);
        service.addParams("baseDate", data1);
        service.addParams("baseDate2", data2);
        service.addParams("emitente_id", emitente_id);
        return getPathPdf(JASPER_TO_PAY_PDFS, document, service);
    }

    private String gerarSellerPdf(String path, String document, String params, Long emitente_id) {
        try {
            service.addParams("seller", params);
            service.addParams("emitente_id", emitente_id);
            return getPathPdf(path, document, service);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    static String getPathPdf(String path, String document, JasperService service) throws IOException {
        byte[] bytes = service.exportPDF(document);
        File file = new File(path + document + ".pdf");
        file.createNewFile();
        FileOutputStream fis = new FileOutputStream(file);
        fis.write(bytes);
        fis.flush();
        fis.close();
        return "/sales/" + document + ".pdf";
    }
}
