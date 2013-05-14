package server;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import server.Shape.Tetrominoes;

/**
 * 
 * @author Hugo Nissar
 * @version 1.0
 *
 */
public class Commands implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6671893133570359686L;
	private ServerThread[] serverThreads;
	private int id;
	
	public Commands(ServerThread[] serverThreads, int id) {
		this.serverThreads = serverThreads;
		this.id = id;
	}

	public void doCommand(int keycode) {
		
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
