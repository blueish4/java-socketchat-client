package backend;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SendData implements Runnable{
	private static String input;
	public void run() {
		System.out.println("Server Startred");
		try{
	        ServerSocket listener = new ServerSocket(9090);
	        try {
	            while (true) {
	                Socket socket = listener.accept();
	                try {
	                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                    out.println(input);
	                } finally {
	                    socket.close();
	                }
	            }
	        }finally {
	        	listener.close();
	        }
	    } catch (Exception e) {}
		
	}
	public static void setInput(String s){
		input = s;
		(new Thread(new SendData())).start();
	}
		
}
