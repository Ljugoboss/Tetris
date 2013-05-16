package client;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The main panel with the background and layout. This panel will hold the important
 * components of the game.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 *
 */
public class Panel extends JPanel{
	
	private static final long serialVersionUID = -7460067586561307363L;
	private Image backgroundImage;
	private GridLayout layout;
	
	/**
	 * Creates and panel with the necessary settings.
	 * @param background The location of the background image.
	 */
	public Panel(String background) {
		this.setFocusable(true);
		layout = new GridLayout(1,2,10,0);
		this.setLayout(layout);
		backgroundImage = new ImageIcon(background).getImage();
		setVisible(true);
	}

	/**
	 * Paint the background.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}
}
