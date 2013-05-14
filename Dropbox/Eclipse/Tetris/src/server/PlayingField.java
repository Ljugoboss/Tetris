package server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import server.Shape.Tetrominoes;

/**
 * 
 * @author Hugo Nissar
 * @version 1.0
 */


public class PlayingField implements ActionListener{
	protected boolean isFallingFinished = false;
	private final int BoardWidth = 10;
	private final int BoardHeight = 22;
	protected boolean isStarted = false;
	protected boolean isPaused = false;
	private Timer timer;
	protected int curX = 0;
	protected int curY = 0;
	protected Shape curPiece;
	private Tetrominoes[] board;
	private boolean rowsRemoved = false;
	private int numRowsRemoved;
	private boolean gameOver = false;
	
	public PlayingField() {
		board = new Tetrominoes[BoardWidth * BoardHeight];
		curPiece = new Shape();
		timer = new Timer(400, this);
		
		start();
		clearBoard();
	}
	Tetrominoes shapeAt(int x, int y) { 		
		return board[(y * BoardWidth) + x]; 
	}

	

	protected void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1)) {
				break;
			}
			newY--;
		}
		pieceDropped();
	}

	protected void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1)) {
			pieceDropped();
		}
	}


	protected void clearBoard()	{
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	private void pieceDropped()	{
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y * BoardWidth) + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	protected void newPiece() {
		curPiece.setRandomShape();
		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();
		
		//If the new piece can't move down at all, it's game over.
		if (!tryMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			gameOver = true;
			System.out.println("Game over");
		}
	}
	
	public boolean gameOver() {
		return gameOver;
	}

	protected boolean tryMove(Shape newPiece, int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) {
				return false;
			}
			if (shapeAt(x, y) != Tetrominoes.NoShape) {
				return false;
			}
		}
		curPiece = newPiece;
		curX = newX;
		curY = newY;
		return true;
	}
	
	public void addRows(int rows) {
		for(int y = BoardHeight-1; y >= rows; y--) {
			for(int x = 0; x < BoardWidth; x++) {
				board[y*BoardWidth+x] = shapeAt(x, y - rows);
			}
		}
		for(int i = 0; i < rows *BoardWidth; i++) {
			board[i] = Tetrominoes.RowShape;
		}
	}
	
	public boolean rowsRemoved() {
		return rowsRemoved ;
	}
	
	public void setRowsRemoved(boolean b) {
		rowsRemoved = b;
		if(b == false) {
			numRowsRemoved = 0;
		}
	}
	
	public int getNumRowsRemoved() {
		return numRowsRemoved;
	}
 
	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; i--) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; j++) {
				if (shapeAt(j, i) == Tetrominoes.NoShape || shapeAt(j, i) == Tetrominoes.RowShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				numFullLines++;
				for (int k = i; k < BoardHeight - 1; k++) {
					for (int j = 0; j < BoardWidth; j++)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
			
		}
		

		if (numFullLines > 0) {
			setRowsRemoved(true);
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			numRowsRemoved = numFullLines;
		}
	}

	
	public void start()	{
		if (isPaused)
			return;

		isStarted = true;
		isFallingFinished = false;
		timer.start();
		clearBoard();

		newPiece();
	}

	protected void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
		} else {
			timer.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();		
		}
	}
	
	public Tetrominoes[] getArray() {
		return board;
	}
	
	public int getCurX() {
		return curX;
	}
	
	public int getCurY() {
		return curY;
	}
	
	public int getBoardWidth() {
		return BoardWidth;
	}
	
	public int getBoardHeight() {
		return BoardHeight;
	}
	
	public Shape getCurPiece() {
		return curPiece;
	}
	
	public void shutDown() {
		timer.stop();
	}
}
