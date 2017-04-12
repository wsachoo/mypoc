package com.att.salesexpress.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.att.salesexpress.microservices.dao.SalesHistoryDao;

@SpringBootApplication
@EnableDiscoveryClient
public class SalesExpressMicroServicesApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SalesExpressMicroServicesApplication.class);
	}
	
	public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SalesExpressMicroServicesApplication.class);
        application.run(args);
	}
	
	@Autowired
	SalesHistoryDao objSalesHistoryDao;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	/*	List<Map<String, Object>> result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeed("Ethernet", 10000, 11);
		for (Map<String, Object> map : result) {
			System.out.println(map.get("DBOR_SOLUTION_ID"));
			System.out.println();
		}*/
		
	}
}
