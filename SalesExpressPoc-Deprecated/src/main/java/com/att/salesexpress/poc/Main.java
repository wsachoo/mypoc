package com.att.salesexpress.poc;

/**
 * Main class create for executing spring boot application. This is not mandatory as we have a single application but may be used
 * in future if we have multiple spring boot application classes.
 * It can be executed with VM argument show below from command prompt.
 * 
 * java -Dspring.profiles.active="PROFILE_NAME" -jar target/SalesExpressPoc-0.0.1-SNAPSHOT.war
 * 
 * When running the application as web archive, just add the spring.profiles.active="PROFILE_NAME" property to tomcat's conf/catalina
 * properties file.
 * 
 * @author sw088d
 *
 */
public class Main {
	public static void main(String[] args) {
			SalesExpressPocApplication.main(args);
	}
}
