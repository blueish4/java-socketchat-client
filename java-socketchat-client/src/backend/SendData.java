package backend;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import frontend.Layout;

public class SendData implements Runnable{
	public void run() {
		System.out.println("Client Started");
		sendMessage();
	}
	
	public static void sendMessage(){
		int i = 0;
		try{
	        ServerSocket listener = new ServerSocket(49150);
	        System.out.println("Listener Opened.");
	        try {
	        	while(true){
		            Socket socket = listener.accept();
		            try {
		                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		                if(Layout.shouldSendMessage)
		                	out.println(Layout.getMessageToSend());
		                //Reset the sending flag to false
		                if(i<2 && Layout.shouldSendMessage){
		                	Layout.shouldSendMessage=false;
		                	System.out.println(i);
		                	i++;
		                }
		            } finally {
		                socket.close();
		            }
	        	}
	        }finally {
	        	System.out.println("Closing listener. Goodbye...");
	        	listener.close();
	        }
	    } catch (Exception e) {}
	}
}
