package br.com.ciaf.admin.services;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.sql.DataSource;

@Service
public class JasperService {
	private static final String JASPER_DIRETORIO = "classpath:jasper/";
	private final ResourceLoader resourceLoader;
	private final DataSource dataSource;
	private Map<String, Object> params = new HashMap<>();
	public JasperService(ResourceLoader resourceLoader, DataSource dataSource) {
        this.dataSource = dataSource;
        this.params.put("IMAGEM_DIRETORIO", JASPER_DIRETORIO);
		this.params.put("REPORT_LOCALE", new Locale("pt", "BR"));
		this.resourceLoader = resourceLoader;
	}
	public void addParams(String key, Object value) {
		this.params.put(key, value);
	}

	public byte[] exportPDF(String document) {
		byte[] bytes = null;
		this.params.forEach( (k, v) -> System.out.println(k + " : " + v));
		try {
				Resource resource = resourceLoader.getResource(JASPER_DIRETORIO.concat(document + ".jasper"));
			try (
					InputStream stream = resource.getInputStream();
					Connection connection = dataSource.getConnection()
			) {
				JasperPrint print = JasperFillManager.fillReport(stream, params, connection);
				bytes = JasperExportManager.exportReportToPdf(print);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        assert bytes != null;
		return bytes;
	}
	public byte[] exportPDFFromXML(String document, String xmlFilePath) {
		byte[] bytes = null;
		this.params.forEach((k, v) -> System.out.println(k + " : " + v));
		try {
			Resource resource = resourceLoader.getResource(JASPER_DIRETORIO.concat(document + ".jasper"));
			try (InputStream stream = resource.getInputStream()) {
				// Use JRXmlDataSource with the XML file path
				net.sf.jasperreports.engine.data.JRXmlDataSource xmlDataSource = new net.sf.jasperreports.engine.data.JRXmlDataSource(xmlFilePath);
				// Generate the JasperPrint object using the XML data source
				JasperPrint print = JasperFillManager.fillReport(stream, params, xmlDataSource);
				// Export the report to a PDF byte array
				bytes = JasperExportManager.exportReportToPdf(print);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert bytes != null;
		System.out.println(bytes.length);
		return bytes;
	}

	public Object covertDate(String data1, String data2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateFirst = LocalDate.parse(data1, formatter);
		LocalDate dateSecond = LocalDate.parse(data2, formatter);
		Date newDate = Date.from(dateFirst.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date newDate2 = Date.from(dateSecond.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Date> list = new ArrayList<>();
		list.add(newDate);
		list.add(newDate2);
		return list;
	}
}


