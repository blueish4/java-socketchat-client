package backend;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import frontend.Layout;

public class client implements Runnable{
	@Override
	public void run() {
		try {
	        InetAddress addr = InetAddress.getByName("127.0.0.1");
	        Socket theSocket = new Socket(addr, 49149);
	        PrintWriter out = new PrintWriter(theSocket.getOutputStream(), true);
	        System.out.println("Connected to "+ theSocket.getInetAddress()+ " on port " + theSocket.getPort() + " from port "+ theSocket.getLocalPort() + " of "+ theSocket.getLocalAddress());
	        out.println(Layout.getMessageToSend());
	        theSocket.close();
	     } 
	     catch (UnknownHostException e) {
	        System.err.println("I can't find " + e  );
	     }
	     catch (SocketException e) {
	        System.err.println("Could not connect to " +e );
	     }
	     catch (IOException e) {
	        System.err.println(e);
	     }
	}
}
