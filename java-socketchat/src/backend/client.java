package backend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import frontend.Layout;

//This class sends out data to the recipients
public class client implements Runnable{
	String message;
	String server;
	
	public client(String svr){
		server = svr;
	}
	
	@Override
	public void run() {
		try{
			// connect to server socket
			Socket ssock = new Socket(server, 49149);
			BufferedReader in = new BufferedReader(new InputStreamReader(ssock.getInputStream()));
				
			// listen for & print incoming messages
			Runnable listener = new Runnable() {
				public void run() {
					while (true) {
						// read client messages
						String input;
						try {
							while ((input = in.readLine()) != null) {
								Layout.recieveMessage("IN: " + input);
							}
						} catch (IOException e) {
							System.err.println("Exception caught when trying to read from client");
						}
					}
				}
			};
			Thread listenThread = new Thread(listener);
			listenThread.start();
			
			// read stdin
			while (true) {
				Layout.recieveMessage("PRIVMSG: "+(new BufferedReader(new InputStreamReader(System.in))).readLine());
			}
		}catch(Exception e){
			System.err.println("Something went wrong "+e);
		}
	}
}
