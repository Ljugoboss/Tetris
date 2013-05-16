package client;

import javax.swing.JFrame;

/**
 * Creates the frame with with the settings we want.
 * @author Hugo Nissar
 * @version 1.0
 */

@SuppressWarnings("serial")
public class Frame extends JFrame{

	/**
	 * Creates the frame with with the settings we want.
	 */
	public Frame() {
		this.setSize(600,600);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Hemlig Karate");
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.revalidate();
	}
}
