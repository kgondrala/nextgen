package com.nextgen.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NextgenApplication implements CommandLineRunner {
	
	@Value("${file.name}")
	private String fileName;
	
	@Autowired
	private BatchFileProcessor batchFileProcessor;
	
	public static void main(String[] args) {
		
		SpringApplication.run(NextgenApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		batchFileProcessor.process(fileName);
	}
}
