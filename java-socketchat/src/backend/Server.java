package backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Server {
	ServerSocket ssock;
	List<ClientHandler> handlers;
	
	public Server() {
		handlers = new ArrayList<ClientHandler>();
		boot();
	}
	
	/**
	 * Print an error to stdout and exit.
	 * 
	 * @param desc		String to print before exiting.
	 * @param exit_code	Integer to exit with.
	 */
	private void exitWith(String desc, int exit_code) {
		System.err.println("ERROR: " + desc);
		System.exit(exit_code);
	}
	
	/**
	 * Start listening on a port.
	 * 
	 * @param port		Port to listen on.
	 * @return			Exit code.
	 */
	private void boot() {
		try {
			ssock = new ServerSocket(49149);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There is already a server running on this machine. Continuing without...", 
					"Socket Chat Program v0.2", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void listen() {
		while (true) {
			try {
				// accept client connection
				Socket client = ssock.accept();
				
				// assign a handler thread
				ClientHandler handler = new ClientHandler(client, this);
				Thread handlerThread = new Thread(handler);
				handlerThread.start();
				
				// add handler to list
				handlers.add(handler);
			} catch (IOException e) {
				exitWith("IOException trying to accept client", 2);
			}
			System.out.println("Client accepted");
		}
	}

	public void relayMessage(String message) {
		for (ClientHandler handler : handlers) {
			handler.sendMessage(message);
		}
	}
	public void removeClient(String addr){
		try {
			System.out.println("Wants to leave: "+addr);
			System.out.println(handlers.size());
			for(ClientHandler handler : handlers){
				System.out.println("DEBUGGING: "+handler.csock.getLocalAddress());
			}
			handlers.remove((new ClientHandler((new Socket(addr,49149)), (new Server()))));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			System.err.println("Hostname non-existant");
		}
	}
	
	public static void main() {
		Server server = new Server();
		
		// set up listener thread (for picking up & assigning clients)
		Runnable listener = new Runnable() {
			public void run() {
				server.listen();
			}
		};
		Thread listenThread = new Thread(listener);
		listenThread.start();
	}
}
