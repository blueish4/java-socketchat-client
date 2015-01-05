package frontend;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Layout extends JFrame{
	private JButton send;
	private static JTextField messageBox;
	private static JTextArea recievedBox;
	private static String uname;
	private static String svrName;
	private static String recievedText="";
	private static Socket ssock; 
	
	public Layout(){
		super("Socket Chat Client v0.1");
		
		//Create the basic layout
		setLayout(new FlowLayout());
		
		//Create the box with the conversation inside it, while making it not editable
		recievedBox = new JTextArea();
		recievedBox.setColumns(25);
		recievedBox.setLineWrap(true);
		recievedBox.setRows(28);
		recievedBox.setEditable(false);
		recievedBox.setFocusable(false);
		
		try {
			recievedBox.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//Create the message box with 20 columns
		messageBox = new JTextField(20);
		
		//Create send button
		send = new JButton("Send");
		
		//Deal with the actions such as the send button being pushed
		HandlerClass handler = new HandlerClass();
		messageBox.addKeyListener(new MyKeyListener());
		send.addActionListener(handler);

		
		//Set the server to connect to and connect to it
		svrName = JOptionPane.showInputDialog("Input the server's IP address.");
		
		//Init the socket bae to deny the NPEs
		try {
			ssock = new Socket("127.0.0.1", 49149);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Get a username to send the data as
		uname = JOptionPane.showInputDialog("What username would you like to use for this session?");
		
		
		//Ack the connection and announce it
		sendMessage(uname + " joined the server");
		
		//Add all of the elements to the layout
		add(recievedBox);
		add(messageBox);
		add(send);
		
	}
	
	//Handler class to accompany actions. 
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//If the action is the send button being pressed, send message
			if(e.getActionCommand()=="Send"){
				sendMessage(getMessageToSend());
			}
		}
	}
	
	public class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			//If the key pressed was enter, send message
			if(ke.getKeyCode()==10){
				sendMessage(getMessageToSend());
			}
		}
	}
	
	public static void sendMessage(String message){
		//erm...this needs to work in a bit.
		//out.println() was used in the other app, similar usage here
		try {
			(new PrintWriter(ssock.getOutputStream(), true)).println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getMessageToSend(){
		//TODO: This will be made into a JSON message when I get around to it, as per the protocol
		String message = uname + ": " + messageBox.getText();
		messageBox.setText("");
		return message;
	}
	
	public static void recieveMessage(String message){
		recievedText += message + "\n";
		recievedBox.setText(recievedText);
	}
	
	public static String getPreviousMessage(){
		String toReturn;
		try{
			toReturn = recievedBox.getText().split("\\n")[recievedBox.getText().split("\\n").length-1];
		}catch(ArrayIndexOutOfBoundsException e){
			toReturn = "";
		}
			return toReturn;
	}
	
	public static String getServerIP(){
		return svrName;
	}
	
	public static void main() {
		try{
			// connect to server socket
			BufferedReader in = new BufferedReader(new InputStreamReader(ssock.getInputStream()));
				
			// listen for & print incoming messages
			Runnable listener = new Runnable() {
				public void run() {
					while (true) {
						// read client messages
						String input;
						try {
							while ((input = in.readLine()) != null) {
								recieveMessage("IN: " + input);
								System.out.println("IN: "+input);
							}
						} catch (IOException e) {
							System.err.println("Exception caught when trying to read from client");
						}
					}
				}
			};
			Thread listenThread = new Thread(listener);
			listenThread.start();
		}catch(Exception e){
			System.err.println("Something went wrong "+e);
			e.printStackTrace();
		}
	}
}
