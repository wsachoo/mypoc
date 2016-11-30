package com.att.salesexpress.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.att.salesexpress.poc.service.DbServiceInterface;

@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(basePackages = { "com.att.salesexpress.poc" })
public class SalesExpressPocApplication implements CommandLineRunner {
	@Autowired
	DbServiceInterface dbServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(SalesExpressPocApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Test change by sachin");
		System.out.println("Printing test message");
	}
}
