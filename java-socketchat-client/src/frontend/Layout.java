package frontend;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Layout extends JFrame{
	private JButton send;
	public Layout(){
		setLayout(new FlowLayout());
		send = new JButton("Send");
		add(send);
	}
	
}
