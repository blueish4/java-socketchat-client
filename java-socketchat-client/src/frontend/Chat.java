package frontend;

import javax.swing.JFrame;

import backend.server;

public class Chat {

	public static void main(String[] args) {
		(new Thread(new server())).start();
		Layout frame = new Layout();
		frame.setSize(300, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}