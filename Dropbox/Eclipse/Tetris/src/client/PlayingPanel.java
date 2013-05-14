package client;

import java.awt.Color;

import javax.swing.JPanel;




public class PlayingPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7708098765651525263L;
	private Paint paint;
	
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
