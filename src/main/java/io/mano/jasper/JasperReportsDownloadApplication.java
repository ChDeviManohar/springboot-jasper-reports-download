package io.mano.jasper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mano.jasper.service.ReportDownloadService;

@SpringBootApplication
public class JasperReportsDownloadApplication implements CommandLineRunner{

	@Autowired
	private ReportDownloadService reportDownloadService;
	
	public static void main(String[] args) {
		SpringApplication.run(JasperReportsDownloadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		reportDownloadService.downloadReports();
	}

}
