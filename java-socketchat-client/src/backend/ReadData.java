package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ReadData implements Runnable{
	public void run(){
		String serverAddress = JOptionPane.showInputDialog( 
				"Enter IP Address of a machine that is\n" + 
				"running the date service on port 9090:");
	    Socket s;
		try {
			s = new Socket(serverAddress, 9090);
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer;
	       	answer = input.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
