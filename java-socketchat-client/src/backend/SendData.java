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
		try{
	        ServerSocket listener = new ServerSocket(49150);
	        System.out.println("Listener Opened.");
	        try {
	        	while(true){
		            Socket socket = listener.accept();
		            String message = Layout.getMessageToSend();
		            try {
		                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		                if(Layout.shouldSendMessage){
		                	System.out.println("Sending message");
		                	for(int i =0;i<2;i++){
		                		System.out.println(message);
		                		while(!message.equals("")){
		                			System.out.println("Message isnt notthin, its: "+message);
		                			out.println(message);
		                			message = Layout.getMessageToSend();
		                		}
		                	}
		                }
		                //Reset the sending flag to false
		                Layout.shouldSendMessage=false;
		                
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
