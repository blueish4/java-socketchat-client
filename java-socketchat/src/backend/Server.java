package backend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import frontend.Layout;

public class Server implements Runnable {
	@Override
	public void run() {
		try {
		      System.out.println("Server Listening");
		      while (true) {
		    	 try{
			    	 ServerSocket ssock = new ServerSocket(49149);
			    	 Socket sock = ssock.accept();
			         System.out.println("Connected to client");
			         BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			         String answer = input.readLine();
			         Layout.recieveMessage(answer);
			         ssock.close();
		    	 }catch (IOException e){
		    		 e.printStackTrace();
		    	 }
		      }
		}finally{
			System.out.println("Server Closing. Goodbye...");
		}
	}
}