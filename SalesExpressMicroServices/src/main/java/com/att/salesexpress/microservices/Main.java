package com.att.salesexpress.microservices;

import com.att.salesexpress.client.ClientApplication;

public class Main {
	public static void main(String[] args) {
		String serverName = "NO-VALUE";

		switch (args.length) {
		case 2:
			System.setProperty("server.port", args[1]);
		case 1:
			serverName = args[0].toLowerCase();
			break;

		default:
			usage();
			return;
		}

		if (serverName.equals("eurekaserver")) {
			EurekaRegistrationServerApplication.main(args);
		} 
		else if (serverName.equals("microservices")) {
			SalesExpressMicroServicesApplication.main(args);
		} 
		else if (serverName.equals("client")) {
			ClientApplication.main(args);
		} 
		else {
			System.out.println("Unknown server type: " + serverName);
			usage();
		}
	}

	protected static void usage() {
		System.out.println("Usage: java -jar ... <server-name> [server-port]");
		System.out.println("     where server-name is 'eurekaserver' " + "or 'microservices' or 'client' and server-port > 1024");
	}
}
