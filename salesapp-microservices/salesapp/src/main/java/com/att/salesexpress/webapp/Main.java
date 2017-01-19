package com.att.salesexpress.webapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main class create for executing spring boot application. This is not
 * mandatory as we have a single application but may be used in future if we
 * have multiple spring boot application classes. It can be executed with VM
 * argument show below from command prompt.
 * 
 * java -Dspring.profiles.active="PROFILE_NAME" -jar
 * target/SalesExpressPoc-0.0.1-SNAPSHOT.war
 * 
 * When running the application as web archive, just add the
 * spring.profiles.active="PROFILE_NAME" property to tomcat's conf/catalina
 * properties file.
 * 
 * @author sw088d
 *
 */
public class Main {
	public static void main(String[] args) {
		// SalesExpressPocApplication.main(args);

		testJsonConversion();
	}

	public static void testJsonConversion() {
		String json = getTestJson();
		firstMethod(json);
		secondMethod(json);
	}

	private static String getTestJson() {
		try {
			InputStream is = new FileInputStream("src/main/resources/test.json");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}

			String fileAsString = sb.toString();
			return fileAsString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static void firstMethod(String fileAsString) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map;
		try {
			JsonNode rootNode = mapper.readTree(fileAsString);
			JsonNode headQuartersNode = rootNode.path("sites").path("headQuarters");
			JsonNode accessSpeedNode = headQuartersNode.path("accessConfig").path("sliderSpeedValue");
			JsonNode portSpeedNode = headQuartersNode.path("portConfig").path("sliderPortSpeedValue");
			System.out.println("Access Speed: " + accessSpeedNode.asText());
			System.out.println("Port Speed: " + portSpeedNode.asText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void secondMethod(String fileAsString) {
		ObjectMapper mapper = new ObjectMapper();
		UserDesignSelectionDO obj;
		try {
			obj = mapper.readValue(fileAsString, new TypeReference<UserDesignSelectionDO>() {
			});
			System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
