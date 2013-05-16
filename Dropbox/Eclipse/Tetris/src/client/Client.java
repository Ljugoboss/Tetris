package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class listens to the server.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 */
public class Client extends Thread {
	private Socket clientSocket = null;
	private InputStream is = null;
	private PrintStream os = null;
	private SpectatePanel sp = null;
	private DataInputStream dis = null;
	private Paint paint = null;
	private int id;
	private int players;

	/**
	 * Creates a client and tries to connect to the server.
	 * @param tetris The main class.
	 * @param paint The drawing panel.
	 */
	public Client(Tetris tetris, Paint paint) {
		this.paint = paint;
		/*
		 * Open a socket on port 2222. Open the input and the output streams.
		 */
		try {			
			clientSocket = new Socket(tetris.getIP(), tetris.getPort());		
			os = new PrintStream(clientSocket.getOutputStream());
			is = clientSocket.getInputStream();
			id = is.read();
			players = is.read();
			dis = new DataInputStream(is);
			this.start();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		} catch (Exception e) {
			System.out.println("FEL!");
		}
		System.out.println("The client started. Type any text. To quit it type 'Ok'.");
	}
	
	/**
	 * Listens after a new array and tells the panel to update.
	 */
	public void run() {
		while(true) {
			byte[] t = readArray();
			byte di = t[0];
			byte[] i = new byte[t.length - 1];
			System.arraycopy(t, 1, i, 0, t.length-1);
			if(id == di) {
				paint.update(i);
			} else {
				if(id < di) {
					di--;
				}
				sp.getPaints()[di].update(i);
			}
		}
	}

	/**
	 * Sends a command.
	 * @param code The number of the command.
	 */
	public void sendCommand(int code) {
		if(os != null) {
			os.println(code);
		}
	}
	
	/**
	 * Disconnects the client from the server.
	 */
	public void dc() {
		if(os != null) {
			os.println("DC");
			os.close();
			try {
				is.close();
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Tries to read from the data input stream.
	 * @return A new byte array if the there's an open input stream, else a new byte array.
	 */
	private byte[] readArray() {
		try {
			if(is != null) {
				byte[] t = new byte[221];
				dis.readFully(t);
				return t;
			}
		} catch(Exception e) {
			System.err.println("Couldn't read from server: " + e);			
		}
		return new byte[221];
	}

	/**
	 * 
	 * @return The number of players.
	 */
	public int getPlayers() {
		return players;
	}

	/**
	 * Sets the SpectatePanel.
	 * @param sp The SpectatePanel.
	 */
	public void setSP(SpectatePanel sp) {
		this.sp = sp;
	}
}