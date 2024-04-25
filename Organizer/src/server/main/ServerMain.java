package server.main;

import server.server.Server;
import shared.options.Options;

public class ServerMain {
	
	public static void main(String[] args) {
		
		Server server = new Server(Options.PORT_USED); // Create a server
		
		System.out.println("SERVER:Server is launched");
		for (int i = 0; i < 1000; i++) {
			System.out.print("-");
		}
		System.out.println("/n");
		
		while(true) {
			server.waitForNextRequest(); // Server is waiting for next request	
			server.executeRequest(); // If request came execute request
		}
	}
}
