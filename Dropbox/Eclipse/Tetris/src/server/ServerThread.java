package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.swing.Timer;

import server.Shape.Tetrominoes;
/**
 * This class listens for commands and tells the Commands class to do what needs to be done.
 * It also sends the updated array, containing the playing field, to the clients.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 *
 */
public class ServerThread extends Thread implements ActionListener {

	private BufferedReader input = null;
	private OutputStream output = null;
	private Commands c;
	private ServerThread[] serverThreads;
	private Timer timer;
	private Server server;
	private PlayingField pf;
	private byte id;

	/**
	 * Create the input and output streams and sends the id and the amount of players to the client.
	 * @param server The actual server.
	 * @param id The id of this thread.
	 * @throws IOException
	 */
	public ServerThread (Server server, byte id)  throws IOException {

		timer = new Timer(400, this);
		this.id = id;

		this.server = server;

		try {
			input  = new BufferedReader(new InputStreamReader(server.getClientSocket().getInputStream()));
			output = server.getClientSocket().getOutputStream();
		}
		catch (IOException e) {
			System.err.println("Couldn't create writer/reader: " + e);
		}

		output.write(id);
		output.write(server.getServerThreads().length);
	}

	/**
	 * This thread listens for commands and send the to the Commands class.
	 */
	public void run(){
		System.out.println("new server thread");
		pf = new PlayingField();		
		serverThreads = server.getServerThreads();
		c = new Commands(serverThreads, id);
		timer.start();

		boolean connected = true;
		System.out.println("Running server Thread");
		try {
			/* As long as we receive data, echo that data back to the client. */
			while (connected) {
				String command = input.readLine(); // Blocks until it recieves something.
				if(command.equals("DC")) { // If the client closed.
					timer.stop();
					input.close();
					output.close();
					connected = false;
					serverThreads[id] = null;
					break;
				}
				c.doCommand(Integer.parseInt(command));
				updateRows();
				sendToAll();
			}
		} catch (IOException e) {
			System.err.println(e);
		}	
	}

	public OutputStream getOutputStream() {
		return output;
	}

	public PlayingField getPlayingField() {
		return pf;
	}

	/**
	 * Send the array to all the cleints.
	 */
	public void sendToAll() {
		byte[] map = createSendArray();


		for (ServerThread st : serverThreads) {
			try {
				if(st != null)
					st.getOutputStream().write(map);
			} catch (IOException e) {
				System.out.println("Filed to send!");
				System.out.println(e);
			}
		}
	}

	/**
	 * Create an array containing bytes instead of Shapes. 
	 * @return An array containing the playing field in bytes and the id of this thread.
	 */
	private byte[] createSendArray() {
		// We need a clone of the original array so we don't ruin it.
		Tetrominoes[] t = pf.getArray().clone(); 
		int curX = pf.getCurX();
		int curY = pf.getCurY();
		int BoardWidth = pf.getBoardWidth();
		Shape curPiece = pf.getCurPiece();

		// Add the piece that is in the air to the array.
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			t[(y * BoardWidth) + x] = curPiece.getShape();
		}

		// Create a new array with the id of the thread and a byte for each shape.
		byte[] map = new byte[t.length+1];
		map[0] = id;

		int i = 1;
		for (Tetrominoes te : t) {
			switch (te) {
			case NoShape:
				map[i] = 0;
				break;
			case ZShape:
				map[i] = 1;
				break;
			case SShape:
				map[i] = 2;
				break;
			case LineShape:
				map[i] = 3;
				break;
			case TShape:
				map[i] = 4;
				break;
			case SquareShape:
				map[i] = 5;
				break;
			case LShape:
				map[i] = 6;
				break;
			case MirroredLShape:
				map[i] = 7;
				break;
			case RowShape:
				map[i] = 8;
				break;
			}
			i++;
		}
		return map;
	}

	public void updateRows() {
		// If any rows are removed since the last update and it's more then one player.
		if(pf.rowsRemoved() && serverThreads.length > 1) {
			for(ServerThread st: serverThreads) {
				// Don't send to me or to someone who lost.
				if(st != this && !st.getPlayingField().gameOver()) {
					switch(pf.getNumRowsRemoved()) {
					case 2:
						st.getPlayingField().addRows(1);
						break;
					case 3: 
						st.getPlayingField().addRows(2);
						break;
					case 4:
						st.getPlayingField().addRows(4);
						break;
					}
				}
			}

			pf.setRowsRemoved(false);
		}
	}

	/**
	 * Send the and update the array for every tick of the timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		updateRows();
		sendToAll();
	}
}
