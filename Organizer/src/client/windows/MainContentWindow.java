package client.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;

public class MainContentWindow {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public MainContentWindow() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.RED);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
