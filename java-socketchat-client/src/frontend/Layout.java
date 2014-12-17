package frontend;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.SendData;

public class Layout extends JFrame{
	private JButton send;
	private static JTextField messageBox;
	private static JTextArea recievedBox;
	private static String recievedText="";
	public static boolean shouldSendMessage = false;
	
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
		//Flag that the message should be sent
		shouldSendMessage=true;
		//Send the message
		SendData.sendMessage();
		//Clear the message box
		messageBox.setText("");
	}
	public static String getMessageToSend(){
		return messageBox.getText();
	}
	public static void recieveMessage(String message){
		recievedText += message+"\n";
		recievedBox.setText(recievedText);
	}
}
