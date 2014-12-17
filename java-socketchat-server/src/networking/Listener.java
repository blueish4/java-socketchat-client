package networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {

	public static void main(String[] args){ 
	
		try ( 
			ServerSocket serverSocket = new ServerSocket(49149);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
    		new InputStreamReader(clientSocket.getInputStream()));
			) {
			
		}catch(Exception e){
				e.printStackTrace();
			}
	}
}
