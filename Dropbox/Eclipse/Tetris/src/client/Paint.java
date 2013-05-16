package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * The panel that paints the playing field.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 */
public class Paint extends JPanel {
	
	private final int BoardWidth = 10;
	private final int BoardHeight = 22;
	private byte[] board;

	private static final long serialVersionUID = 4669849797414095335L;

	/**
	 * Creates a new panel.
	 * @param width The width of the board.
	 * @param height The height of the board.
	 */
	public Paint(int width, int height) {
		board = new byte[BoardWidth * BoardHeight];
		this.setBackground(new Color(102, 204, 204));
		size(width,height);
	}
	
	/**
	 * Update the panel according to the new array.
	 * @param map The new array that with the updates.
	 */
	public void update(byte[] map) {
		this.board = map;
		repaint();
		updateUI();
	}
	
	/**
	 * Paint the panel.
	 */
	public void paint(Graphics g) {
		// This is used as a buffer.
		Image offScreen = this.createImage(this.getWidth(), this.getHeight());
		draw(offScreen.getGraphics());
		
		g.drawImage(offScreen, 0, 0, null);
	}
	
	/**
	 * Make a buffer for the panels Graphics.
	 * @param g
	 */
	public void draw(Graphics g) {
		super.paint(g);
		drawGrid(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();


		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				int shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != 0)
					drawSquare(g, 0 + j * squareWidth(),
							boardTop + i * squareHeight(), shape);
			}
		}
	}
	
	/**
	 * Draw a grid so it's easier to see where the pieces are.
	 * @param g
	 */
	private void drawGrid(Graphics g) {
		g.setColor(Color.gray);
		
		int x = (int) this.getWidth()/BoardWidth;
		
		for(int i = 0; i <= BoardWidth; i++) {
			g.drawLine((i * x), 0, (i * x), this.getHeight());
		}
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Draw a square according to the board array.
	 * @param g
	 * @param x The x-position of the square.
	 * @param y The y-position of the square.
	 * @param shape What kind of shape is it?
	 */
	private void drawSquare(Graphics g, int x, int y, int shape) {
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), 
				new Color(102, 204, 102), new Color(102, 102, 204), 
				new Color(204, 204, 102), new Color(204, 102, 204), 
				new Color(255, 0, 0), new Color(218, 170, 0),
				Color.GRAY};

		//Set the color and draw the square.
		Color color = colors[shape];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		//Make a nice border around the square
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1,
				x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
				x + squareWidth() - 1, y + 1);

	}
	
	int squareWidth() {
		return (int) this.getWidth() / BoardWidth; 
	}
	
	int squareHeight() { 
		return (int) this.getHeight() / BoardHeight; 
	}
	
	/**
	 * Set a size for the panel.
	 * @param width
	 * @param height
	 */
	public void size(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
	}
	
	int shapeAt(int x, int y) { 		
		return board[(y * BoardWidth) + x]; 
	}
}
