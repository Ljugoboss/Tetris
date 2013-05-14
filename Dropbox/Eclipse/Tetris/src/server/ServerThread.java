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
 * @author Hugo Nissar
 * @author Jonas Sj�berg
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
	 * 
	 * @param ine The IP-address to the client
	 * @param ds The DatagramSocket the server uses
	 * @throws Exception
	 */
	public ServerThread (Server server, byte id)  throws IOException {

		timer = new Timer(100, this);
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
	 * ......
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
				String command = input.readLine();
				if(command.equals("dc")) {
					timer.stop();
					input.close();
					output.close();
					connected = false;
					serverThreads[id] = null;
					break;
				}
				c.doCommand(Integer.parseInt(command));
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

	private byte[] createSendArray() {
		Tetrominoes[] t = pf.getArray().clone();
		int curX = pf.getCurX();
		int curY = pf.getCurY();
		int BoardWidth = pf.getBoardWidth();
		Shape curPiece = pf.getCurPiece();

		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			t[(y * BoardWidth) + x] = curPiece.getShape();
		}

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(pf.rowsRemoved() && serverThreads.length > 1) {
			for(ServerThread st: serverThreads) {
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
		sendToAll();
	}
}
