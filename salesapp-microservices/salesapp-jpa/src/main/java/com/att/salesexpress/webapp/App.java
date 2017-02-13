package com.att.salesexpress.webapp;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.att.salesexpress.webapp.dao.MyDaoOracle;
import com.att.salesexpress.webapp.dao.MyDaoPostgreSql;
import com.att.salesexpress.webapp.entity.oracle.SalesRulesOracle;
import com.att.salesexpress.webapp.entity.postgres.SalesRulesPostgreSql;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "com.att.salesexpress.webapp" })
public class App {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(App.class);
		ApplicationContext ctx = application.run(args);
		MyDaoOracle objMyDao = ctx.getBean(MyDaoOracle.class);
		List<SalesRulesOracle> list = objMyDao.findAll();
		
		for (SalesRulesOracle salesRules : list) {
			System.out.println(salesRules);
		}
		
		MyDaoPostgreSql objMyDaoPostgreSql = ctx.getBean(MyDaoPostgreSql.class);
		List<SalesRulesPostgreSql> list2 = objMyDaoPostgreSql.findAll();
		
		for (SalesRulesPostgreSql salesRules : list2) {
			System.out.println(salesRules);
		}

	}
}
