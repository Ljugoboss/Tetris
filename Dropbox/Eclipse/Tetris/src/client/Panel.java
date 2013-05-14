package client;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7460067586561307363L;
	private Image backgroundImage;
	private GridLayout layout;

	public Panel(String background) {
		this.setFocusable(true);
		layout = new GridLayout(1,2,10,0);
		this.setLayout(layout);
		backgroundImage = new ImageIcon(background).getImage();
		setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}
}
