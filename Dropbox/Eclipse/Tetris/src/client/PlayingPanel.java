package client;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * An unnecessary panel that was needed in an earlier version of the program and is therefore 
 * still here. It creates the Paint panel and ads it to itself.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 *
 */
public class PlayingPanel extends JPanel{
	
	private static final long serialVersionUID = 7708098765651525263L;
	private Paint paint;
	
	/**
	 * Creates the panel with the necessary settings.
	 */
	public PlayingPanel() {
		this.setBackground(new Color(102, 204, 204));
		paint = new Paint(300, 595);
		setSize(300,600);
		
		this.add(paint);
		paint.setVisible(true);
	}
	
	public Paint getPaint() {
		return paint;
	}
}
