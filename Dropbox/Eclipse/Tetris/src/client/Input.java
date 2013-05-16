package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Creates a KeyListener that listens for new key presses.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 *
 */
public class Input implements KeyListener {
	
	private Client client;
	
	/**
	 * Initialize variables. 
	 * @param client
	 */
	public Input(Client client) {
		this.client = client;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Makes the client send a command whenever a key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		client.sendCommand(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
