package client;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * This panel holds the other players playing panels.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 */
public class SpectatePanel extends JPanel{

	private static final long serialVersionUID = -2611442754921355144L;

	private Paint[] paints;
	
	/**
	 * Creates the other players playing fields and the panel holding them.
	 * @param players The amount of players that will play.
	 */
	public SpectatePanel(int players) {
		int wGap = 10;
		int hGap = 10;
		GridLayout layout = new GridLayout(2,2,hGap,wGap);
		this.setLayout(layout);
		this.setBackground(Color.black);
		try {
			paints = new Paint[players - 1];
			for(int i = 0; i < players - 1; i++) { //Create the playing fields.
				paints[i] = new Paint(150, 300);
				add(paints[i]);
			}
			for(int i = 0; i < 5-players; i++) { //Adds empty panels so it looks pretty.
				JPanel p= new JPanel();
				p.setBackground(new Color(102, 204, 204));
				this.add(p);
			}
		} catch(NegativeArraySizeException e) {
			System.out.println("No server created!");
		}
	}

	public Paint[] getPaints() {
		return paints;
	}
}
