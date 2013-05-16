package client;

import java.awt.event.WindowEvent;

/**
 * Listens to any actions made on the window.
 * @author Hugo Nissar
 * @author Jonas Sj�berg
 *
 */
public class WindowListener implements java.awt.event.WindowListener {
	private Tetris tetris;
	
	public WindowListener(Tetris tetris) {
		this.tetris = tetris;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	/**
	 * Disconnects the client from the server when the window closes.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		try {
		tetris.getClient().dc();
		} catch(NullPointerException npe) {
			System.out.println("No client to dc!");
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {		
	}
}
