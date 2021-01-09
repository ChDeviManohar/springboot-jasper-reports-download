package io.mano.jasper.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.mano.jasper.model.Employee;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class DefaultReportDownloadService implements ReportDownloadService {
	
	private static final String FILE_PATH_TO_SAVE = "C:\\Users\\DeviManohar\\Desktop\\reports\\";
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReportDownloadService.class);

	@Override
	public void downloadReports() {
		try {
			List<Employee> employees = buildEmployees();
			JasperPrint jasperPrint = buildJasperPrint(employees);
			
			downloadPdfFile(jasperPrint);
			downloadExcelFile(jasperPrint);
			downloadCsvFile(jasperPrint);
			downloadHtmlFile(jasperPrint);
			
		} catch(Exception e) {
			LOGGER.error("Failed due to:", e);
		}
		

	}
	
	private List<Employee> buildEmployees() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "Manohar", "SE", "IT"));
		employees.add(new Employee(2, "Nadeem", "MGR", "IT"));
		employees.add(new Employee(3, "Suresh", "SE", "IT"));
		employees.add(new Employee(4, "Rajesh", "SE", "IT"));
		employees.add(new Employee(5, "Rakesh", "SE", "IT"));
		return employees;
	}

	private JasperPrint buildJasperPrint(List<Employee> employees) throws JRException {
		InputStream stream = this.getClass().getResourceAsStream("/report.jrxml");
		JasperReport report = JasperCompileManager.compileReport(stream);
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

		// Adding the additional parameters
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Manohar");

		return JasperFillManager.fillReport(report, parameters, source);
	}

	private void downloadPdfFile(JasperPrint jasperPrint) {
		try {
			JRPdfExporter pdfExporter = new JRPdfExporter();

			pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(FILE_PATH_TO_SAVE + "employeeReport.pdf"));

			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("Manohar");
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");

			pdfExporter.setConfiguration(reportConfig);
			pdfExporter.setConfiguration(exportConfig);

			pdfExporter.exportReport();
			LOGGER.info("{} file saved successfully", FILE_PATH_TO_SAVE + "employeeReport.pdf");
		} catch (Exception e) {
			LOGGER.error("Failed in exporting pdf file, due to:", e);
		}
	}
	
	private void downloadExcelFile(JasperPrint jasperPrint) {
		try {
			JRXlsxExporter excelExporter = new JRXlsxExporter();

			excelExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			excelExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(FILE_PATH_TO_SAVE + "employeeReport.xlsx"));

			SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
			reportConfig.setSheetNames(new String[] { "Employee Data" });

			excelExporter.setConfiguration(reportConfig);
			excelExporter.exportReport();
			LOGGER.info("{} file saved successfully", FILE_PATH_TO_SAVE + "employeeReport.xlsx");
		} catch (Exception e) {
			LOGGER.error("Failed in exporting excel file, due to:", e);
		}
	}

	private void downloadCsvFile(JasperPrint jasperPrint) {
		try {
			JRCsvExporter csvExporter = new JRCsvExporter();

			csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			csvExporter.setExporterOutput(new SimpleWriterExporterOutput(FILE_PATH_TO_SAVE + "employeeReport.csv"));

			csvExporter.exportReport();
			LOGGER.info("{} file saved successfully", FILE_PATH_TO_SAVE + "employeeReport.csv");
		} catch (Exception e) {
			LOGGER.error("Failed in exporting csv file, due to:", e);
		}
	}

	private void downloadHtmlFile(JasperPrint jasperPrint) {
		try {
			HtmlExporter htmlExporter = new HtmlExporter();

			htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(FILE_PATH_TO_SAVE + "employeeReport.html"));

			htmlExporter.exportReport();
			LOGGER.info("{} file saved successfully", FILE_PATH_TO_SAVE + "employeeReport.html");
		} catch (Exception e) {
			LOGGER.error("Failed in exporting html file, due to:", e);
		}
	}

	

}
