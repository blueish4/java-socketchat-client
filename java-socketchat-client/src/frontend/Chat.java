package frontend;

import javax.swing.JFrame;

import backend.ReadData;
import backend.SendData;

public class Chat {

	public static void main(String[] args) {
		Layout frame = new Layout();
		frame.setSize(300, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		(new Thread(new ReadData())).start();
		(new Thread(new SendData())).start();
		
	}
}