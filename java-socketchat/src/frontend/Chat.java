package frontend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import backend.Server;

public class Chat {

	public static void main(String[] args) {
		String[] options = {"yes", "no"};
		if(JOptionPane.showOptionDialog(null, "Should this instance also run a server?", "Java Chat program v0.1", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0])==0){
			Server.main();
			System.out.println("Running server.");
		}
		Layout frame = new Layout();
		Layout.main();
		frame.setSize(300, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}