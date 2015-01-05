package backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	int port;
	ServerSocket ssock;
	List<ClientHandler> handlers;
	
	public Server(int port) {
		this.port = port;
		this.handlers = new ArrayList<ClientHandler>();
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
			ssock = new ServerSocket(port);
		} catch (IOException e) {
			exitWith("IOException on port " + port, 1);
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

	//public void relayMessage(String[] message) {
	public void relayMessage(String message) {
		//System.out.println("Heya, I'm sending messages, yo.");
		for (ClientHandler handler : handlers) {
			handler.sendMessage(message);
		}
	}
	
	public static void main() {
		Server server = new Server(49149);
		
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
