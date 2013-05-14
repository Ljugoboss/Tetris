package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Input implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2679329980982816181L;
	private Client client;
	public Input(Client client) {
		this.client = client;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		client.sendCommand(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
