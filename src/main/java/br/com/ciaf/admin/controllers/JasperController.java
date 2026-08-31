package br.com.ciaf.admin.controllers;

import java.io.*;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import br.com.ciaf.admin.repositories.BudgetsRepository;
import br.com.ciaf.admin.services.JasperService;

@RestController
@RequestMapping({ "/reports" })
public class JasperController {
	private static final String JASPER_BUDGET_PDFS = "/var/www/ciafonline.com.br/budgets/";
	private static final String JASPER_SALE_PDFS = "/var/www/ciafonline.com.br/sales/";
	private static final String JASPER_TO_PAY_PDFS = "/var/www/ciafonline.com.br/reports/";
	private static final String JASPER_DANFES_PDFS = "/var/www/ciafonline.com.br/danfes/";

	@Autowired
    private JasperService service;
	@CrossOrigin
	@GetMapping(path = {"/{id}/{document}"})
	public String saveAndShowBudgets(@PathVariable Long id, @PathVariable String document) throws IOException {
		String theDocument = "";
		if(document.equals("Budget")) {
			theDocument = gerarDocumentsById(id, JASPER_BUDGET_PDFS,"Orcamento", "my_budget_id");
		}
		else if(document.equals("Venda")){
			theDocument = gerarDocumentsById(id, JASPER_SALE_PDFS, "Venda", "my_sale_id");

		}
		else if(document.equals("A_Pagar")){
			theDocument =  gerarDocumentsById(id, JASPER_TO_PAY_PDFS, "A_Pagar", null);
		}
		return theDocument;
	}
	@CrossOrigin
	@GetMapping(path = {"/{id}/{document}/emitente/{emitente_id}"})
	public String saveAndShowBudgetsWithEmitente(
			@PathVariable Long id,
			@PathVariable String document,
			@PathVariable Long emitente_id
					) throws IOException {
		String theDocument = "";
        switch (document) {
            case "Budget":
                theDocument = gerarDocumentsByIdAndEmitenteId(id, JASPER_BUDGET_PDFS, "Orcamento", "my_budget_id", emitente_id);
                break;
            case "Venda":
                theDocument = gerarDocumentsByIdAndEmitenteId(id, JASPER_SALE_PDFS, "Venda", "my_sale_id", emitente_id);

                break;
            case "A_Pagar":
                theDocument = gerarDocumentsByIdAndEmitenteId(id, JASPER_TO_PAY_PDFS, "A_Pagar", null, emitente_id);
                break;
        }
		return theDocument;
	}

	private String gerarDocumentsById(Long id, String path, String document, String params) throws IOException{
		if (params != null){
			service.addParams(params, id);
		}
		try {
			byte[] bytes = service.exportPDF(document);
			File file = new File(path + document+"-" + id + ".pdf");
			file.createNewFile();
			FileOutputStream fis = new FileOutputStream(file);
			fis.write(bytes);
			fis.flush();
			fis.close();
			return path + document+"-" + id + ".pdf";
		}catch (Exception e){
			return "Error in generation pdf in controller => " + e;
		}
	}
	private String gerarDocumentsByIdAndEmitenteId(Long id, String path, String document, String params, Long emitente_id) throws IOException{
		if (params != null){
			service.addParams(params, id);
		}
		service.addParams("emitente_id", emitente_id);
		try {
			byte[] bytes = service.exportPDF(document);
			File file = new File(path + document+"-" + id + ".pdf");
			file.createNewFile();
			FileOutputStream fis = new FileOutputStream(file);
			fis.write(bytes);
			fis.flush();
			fis.close();
			return path + document+"-" + id + ".pdf";
		}catch (Exception e){
			System.out.println("Error in generation pdf in controller => " + e);
			return "Error in generation pdf in controller => " + e;
		}
	}



	@CrossOrigin
	@GetMapping(path = {"emitente/{document}/{emitente_id}"})
	public String geralReportsWithEmitente(
			@PathVariable String document,
			@PathVariable Long emitente_id

	) throws IOException {
		return gerarBasicPdfWithEmitente(document, emitente_id);
	}

	private String gerarBasicPdfWithEmitente(String document, Long id) throws IOException{
//		System.out.println("into gerarBasicPdfWithEmitente");
		service.addParams("emitente_id", id);

		byte[] bytes = service.exportPDF(document);
		File file = new File(JASPER_TO_PAY_PDFS + document + ".pdf");
		file.createNewFile();
		FileOutputStream fis = new FileOutputStream(file);
		fis.write(bytes);
		fis.flush();
		fis.close();
		return JASPER_TO_PAY_PDFS + document + ".pdf";
	}
	private String gerarBasicPdfWithEmitenteByXml(String document, String xmlPath, Long id) throws IOException{
		service.addParams("emitente_id", id);
		byte[] bytes = service.exportPDFFromXML(document, xmlPath);
		File file = new File(JASPER_DANFES_PDFS + document + ".pdf");
		file.createNewFile();
		FileOutputStream fis = new FileOutputStream(file);
		fis.write(bytes);
		fis.flush();
		fis.close();
		return JASPER_DANFES_PDFS + document + ".pdf";
	}

	@CrossOrigin
	@GetMapping(path = {"/{document}/{data1}/{data2}"})
	public String geralReportsPerPeriod(@PathVariable String document,
										@PathVariable String data1,
										@PathVariable String data2) throws IOException {
		List<Date> dateList = (List<Date>) service.covertDate(data1, data2);
		String theDocument = "";
		service.addParams("baseDate", dateList.get(0));
		service.addParams("baseData2", dateList.get(1));
		byte[] bytes = service.exportPDF(document);
		if (document.equals("A_PagarPeriod")){
			File file = new File(JASPER_TO_PAY_PDFS + "A_PagarPeriod-" + data1 + "E" + data2 +".pdf");
			file.createNewFile();
			FileOutputStream fis = new FileOutputStream(file);
			fis.write(bytes);
			fis.flush();
			fis.close();
			theDocument =  "A_PagarPeriod-" + data1+ "E" + data2 +".pdf";
		}
		else if(document.equals("A_ReceberPeriod")){
			File file = new File(JASPER_TO_PAY_PDFS + "A_ReceberPeriod-" + data1 + "E" + data2 +".pdf");
			file.createNewFile();
			FileOutputStream fis = new FileOutputStream(file);
			fis.write(bytes);
			fis.flush();
			fis.close();
			theDocument =  "A_ReceberPeriod-" + data1+ "E" + data2 +".pdf";
		}
		return theDocument;
	}
	@CrossOrigin
	@GetMapping(path = {"/mes/{document}/{mes}/{emitente_id}"})
	public String geralReportsPerMonth(@PathVariable String document,
										@PathVariable String mes,
										@PathVariable Long emitente_id
										) throws IOException {
		String theDocument = "";
		if(document.equals("A_PagarMensal")){
			theDocument = gerarReportsEmitente("A_PagarMensal","mes", mes, emitente_id );
		}
		else if(document.equals("A_PagarMensalPaga")){
			theDocument =  gerarReportsEmitente("A_PagarMensalPaga","mes", mes, emitente_id );
		}
		else if(document.equals("A_PagarMensalNaoPaga")){
			theDocument = gerarReportsEmitente("A_PagarMensalNaoPaga","mes", mes, emitente_id );
		}
		else if(document.equals("A_ReceberMensal")){
			theDocument = gerarReportsEmitente("A_ReceberMensal","mes", mes, emitente_id );
		}else if(document.equals("PersonsBirth")){//
			theDocument = gerarReportsEmitente("PersonsBirth","basic_parameter", mes, emitente_id );
		}
		return theDocument;
	}
//	private String gerarReports(String document, String params, Object value, Long id) throws IOException {
//		String theDocument = "";
//		service.addParams(params, value);
//		service.addParams("emitente_id", id);
//		byte[] bytes = service.exportPDF(document);
//		File file = new File(JASPER_TO_PAY_PDFS + document + "-" + value +".pdf");
//		file.createNewFile();
//		FileOutputStream fis = new FileOutputStream(file);
//		fis.write(bytes);
//		fis.flush();
//		fis.close();
//		theDocument = document + "-" + value +".pdf";
//		return theDocument;
//	}
	private String gerarReportsEmitente(String document, String params, Object value, Long id) throws IOException {
		String theDocument = "";
		service.addParams(params, value);
		service.addParams("emitente_id", id);
		byte[] bytes = service.exportPDF(document);
		File file = new File(JASPER_TO_PAY_PDFS + document + "-" + value +".pdf");
		file.createNewFile();
		FileOutputStream fis = new FileOutputStream(file);
		fis.write(bytes);
		fis.flush();
		fis.close();
		theDocument = document + "-" + value +".pdf";
		return theDocument;
	}
	@CrossOrigin
	@GetMapping(path = {"/products/{document}/{value}/{emitente_id}"})
	public String productsReports(
			@PathVariable String document,
			@PathVariable int value,
			@PathVariable Long emitente_id
	) throws IOException {
		if (document.equals("BiggerStock") || document.equals("SmallerStock")) {
			return gerarReportsEmitente(document, "minimum_stock", value, emitente_id);
		}
		 return gerarReportsEmitente(document, "basic_parameter", value, emitente_id);
	}

	@CrossOrigin
	@GetMapping(path = {"/products-value/{document}/{value}/{emitente_id}"})
	public String productsReports(
			@PathVariable String document,
			@PathVariable double value,
			@PathVariable Long emitente_id
	) throws IOException {

			return gerarReportsEmitente(document, "basic_parameter", value, emitente_id);
	}
	@CrossOrigin
	@GetMapping(path = {"/products-name/{document}/{name}/{emitente_id}"})
	public String productsReportsByName(
			@PathVariable String document,
			@PathVariable String name,
			@PathVariable Long emitente_id
	) throws IOException {
		return gerarReportsEmitente(document, "start_name", name, emitente_id);
	}
	@CrossOrigin
	@GetMapping(path = {"/persons/{document}/{value}/{emitente_id}"})
	public String personsReports(
			@PathVariable String document,
			@PathVariable String value,
			@PathVariable Long emitente_id
	) throws IOException {
		 return gerarReportsEmitente(document, "basic_parameter", value, emitente_id);
	}
	//to see PDF forward spring boot application
	@CrossOrigin
	@GetMapping(path = {"/show/{id}/{document}"})
	public ResponseEntity<InputStreamResource> saveAndShowBudgetsAndSales(@PathVariable Long id, @PathVariable String document) throws IOException {
		  File file = null;
		  String theDocument = "";		  
		  if(document.equals("Budget")) {
			  service.addParams("my_budget_id", id);
			  byte[] bytes = service.exportPDF(document);
			  file = new File(JASPER_BUDGET_PDFS + "Orcamento-" + id + ".pdf");
			  file.createNewFile();
			  FileOutputStream fis = new FileOutputStream(file);          
			  fis.write(bytes);
			  fis.flush();
			  fis.close();          
			  theDocument = "Orcamento-" + id + ".pdf";
		  }
		  else if(document.equals("Venda")){
			  service.addParams("my_sale_id", id);
			  byte[] bytes = service.exportPDF(document);
			  file = new File(JASPER_SALE_PDFS + "Sale-" + id + ".pdf");
			  file.createNewFile();
			  FileOutputStream fis = new FileOutputStream(file);          
			  fis.write(bytes);
			  fis.flush();
			  fis.close();          
			  theDocument = "Venda-" + id + ".pdf";
		  }
		  HttpHeaders headers = new HttpHeaders();      
	        headers.add("content-disposition", "inline;filename=" +theDocument);
	        
	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		  return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(file.length())
	                .contentType(MediaType.parseMediaType("application/pdf"))
	                .body(resource);
	}

	@CrossOrigin
	@GetMapping(path = {"/nfe/{document}/{emitente_id}"})
	public String geralReportsNfe(
			@PathVariable String document,
			@PathVariable Long emitente_id
	) throws IOException {
		String xmlPath = "/var/www/ciafonline.com.br/xmls/NFe_156.xml";
		return gerarBasicPdfWithEmitenteByXml(document,xmlPath, emitente_id);
	}


}
