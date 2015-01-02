package backend;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import frontend.Layout;

//This class sends out data to the recipients
public class client implements Runnable{
	String message;
	String recipient;
	
	public client(String recip, String msg){
		System.out.println("PREVMSG: "+Layout.getPreviousMessage());
		message = msg;
		if(recip.startsWith("/")){
			recipient = recip.substring(1);
		}else{
			recipient = recip;
		}
		
	}
	
	@Override
	public void run() {
		try {
			if(Layout.getPreviousMessage().equals(message)){
				System.out.println("Not sending- previous message is this message.");
			}else{
		        InetAddress addr = InetAddress.getByName(recipient);
		        Socket theSocket = new Socket(addr, 49149);
		        PrintWriter out = new PrintWriter(theSocket.getOutputStream(), true);
		        System.out.println(
		        		"Connected to "+ theSocket.getInetAddress()+ 
		        		" on port " + theSocket.getPort() + 
		        		" from port "+ theSocket.getLocalPort() + 
		        		" of "+ theSocket.getLocalAddress());
		        out.println(message);
		        theSocket.close();
		     } 
		}catch (UnknownHostException e) {
			System.err.println("I can't find " + e  );
		}catch (SocketException e) {
			System.err.println("Could not connect to " +e );
		}catch (IOException e) {
		   System.err.println(e);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
