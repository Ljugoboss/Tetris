package server;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import server.Shape.Tetrominoes;

/**
 * This class tells the playing field to do something after a command is sent 
 * from a client to the server.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 *
 */
public class Commands implements Serializable {

	private static final long serialVersionUID = 6671893133570359686L;
	private ServerThread[] serverThreads;
	private int id;
	
	/**
	 * 
	 * @param serverThreads The array with all the server threads.
	 * @param id The id that belongs to this thread.
	 */
	public Commands(ServerThread[] serverThreads, int id) {
		this.serverThreads = serverThreads;
		this.id = id;
	}

	/**
	 * Do something when a command i delivered.
	 * @param keycode The code of the pressed key.
	 */
	public void doCommand(int keycode) {
		
		/**
		 * If the the game isn't started or there is no piece to move, don't do anything. 
		 */
		if (!serverThreads[id].getPlayingField().isStarted || serverThreads[id].getPlayingField().curPiece.getShape() == Tetrominoes.NoShape) {  
            return;
        }
		//pause if p is pressed
        if (keycode == 'p' || keycode == 'P') {
        	for(ServerThread st : serverThreads) {
        		st.getPlayingField().pause();
        	}
            return;
        }
        //Don't do anything if the game is paused
        if (serverThreads[id].getPlayingField().isPaused) {
            return;
        }
        //Move the piece according to the command
        switch (keycode) {
        case KeyEvent.VK_LEFT:
            serverThreads[id].getPlayingField().tryMove(serverThreads[id].getPlayingField().curPiece, serverThreads[id].getPlayingField().curX - 1, serverThreads[id].getPlayingField().curY);
            break;
        case KeyEvent.VK_RIGHT:
            serverThreads[id].getPlayingField().tryMove(serverThreads[id].getPlayingField().curPiece, serverThreads[id].getPlayingField().curX + 1, serverThreads[id].getPlayingField().curY);
            break;
        case KeyEvent.VK_DOWN:
            serverThreads[id].getPlayingField().tryMove(serverThreads[id].getPlayingField().curPiece.rotateRight(), serverThreads[id].getPlayingField().curX, serverThreads[id].getPlayingField().curY);
            break;
        case KeyEvent.VK_UP:
            serverThreads[id].getPlayingField().tryMove(serverThreads[id].getPlayingField().curPiece.rotateLeft(), serverThreads[id].getPlayingField().curX, serverThreads[id].getPlayingField().curY);
            break;
        case KeyEvent.VK_SPACE:
            serverThreads[id].getPlayingField().dropDown();
            break;
        case 'd':
            serverThreads[id].getPlayingField().oneLineDown();
            break;
        case 'D':
            serverThreads[id].getPlayingField().oneLineDown();
            break;
        }
	}
}
