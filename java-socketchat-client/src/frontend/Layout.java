package frontend;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.SendData;

public class Layout extends JFrame{
	private JButton send;
	private JTextField messageBox;
	private JTextArea recievedBox;
	private String recievedText="";
	
	public Layout(){
		//Create the basic layout
		super("Socket Chat Client v0.1");
		setLayout(new FlowLayout());
		
		//Create the box with the conversation inside it, while making it not editable
		recievedBox = new JTextArea();
		recievedBox.setColumns(25);
		recievedBox.setLineWrap(true);
		recievedBox.setRows(28);
		recievedBox.setEditable(false);
		recievedBox.setFocusable(false);
		
		//Create the message box with 20 columns
		messageBox = new JTextField(20);
		
		//Create send button
		send = new JButton("Send");
		
		//Deal with the actions such as the send button being pushed
		HandlerClass handler = new HandlerClass();
		messageBox.addKeyListener(new MyKeyListener());
		send.addActionListener(handler);
		
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
				sendMessage();
			}
		}
	}
	public class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			//If the key pressed was enter, send message
			if(ke.getKeyCode()==10){
				sendMessage();
			}
		}
	}
	public void sendMessage(){
		//TODO: Actually send the message.
		SendData.setInput(messageBox.getText());
		recieveMessage();
		
		//Set the text of the conversation to include the message sent
		//recievedText += messageBox.getText()+"\n";
		recievedBox.setText(recievedText);
		
		//Clear the message box
		messageBox.setText("");
	}
	public void recieveMessage(){
		String serverAddress = JOptionPane.showInputDialog( 
				"Enter IP Address of a machine that is\n" + 
				"running the date service on port 9090:");
	    Socket s;
		try {
			s = new Socket(serverAddress, 9090);
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer;
	       	answer = input.readLine();
	       	recievedText += answer;
	       	recievedBox.setText(recievedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
