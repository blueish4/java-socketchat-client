package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import frontend.Layout;

/*Credit to Ben Orchard for this section
 * https://github.com/raehik/networkChat
 */


public class ClientHandler implements Runnable {
	Socket csock;
	Server server;
	PrintWriter out;
	BufferedReader in;
	
	public ClientHandler(Socket client, Server server) {
		this.csock = client;
		this.server = server;
		try {
			out = new PrintWriter(csock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(csock.getInputStream()));
		} catch (IOException e) {
			System.err.println("Exception caught when trying to set up IO with a client");
		}
	}
	
	public void run() {
		while (true) {
			// read client messages
			String input;
			try {
				while ((input = in.readLine()) != null) {
					//Send out the messages
					Layout.sendMessage(input);
					//System.out.println("I got a message!: " + input);
				}
			} catch (IOException e) {
				System.err.println("Exception caught when trying to read from client: ClientHandler.java");
			}
		}
	}

	//public void sendMessage(String[] message) {
	public void sendMessage(String message) {
		out.println(message);
	}
}
