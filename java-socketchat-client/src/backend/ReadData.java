package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

import frontend.Layout;

public class ReadData implements Runnable{
	private String svrIP;
	public void run(){
		if(svrIP==null){
			setServerIP();
		}
		System.out.println("Server IP: "+svrIP);
	    Socket s;
		try {
			while(true){
				s = new Socket(svrIP, 49150);
				BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
	       		String answer = input.readLine();
	       		if(answer!=null)
	       			Layout.recieveMessage(answer);
	       		s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	public void setServerIP(){
		svrIP = JOptionPane.showInputDialog( 
				"Enter IP Address of a machine that is\n" + 
				"running the date service on port 49150:");
	}
}
