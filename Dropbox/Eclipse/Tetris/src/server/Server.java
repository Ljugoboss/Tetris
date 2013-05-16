package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class creates a server and listens for clients to connect.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 *
 */
public class Server extends Thread {
	private ServerThread[] serverThreads;
	private ServerSocket echoServer = null;
	private Socket clientSocket = null;
	private byte playersConnected = 0;
	
	/**
	 * Create a new server.
	 * @param numPlayers The amount of players who will join.
	 */
	public Server(int numPlayers) {
		
		serverThreads = new ServerThread[numPlayers];
	}
	
	/**
	 * Creates the server and listens for clients.
	 */
	public void run() {

		/*
		 * Open a server socket on port 4444. Note that we can't choose a port less
		 * than 1023 if we are not privileged users (root).
		 */
		try {
			echoServer = new ServerSocket(4444);
		} catch (IOException e) {
			System.out.println(e);
		}

		/*
		 * Create a socket object from the ServerSocket to listen to and accept
		 * connections. Open input and output streams.
		 */
		System.out.println("The server started. To stop it press <CTRL><C>.");
		while(true) {
			try {
				System.out.println("Waiting for client");
				clientSocket = echoServer.accept(); // Block until a client connects.
				ServerThread st = new ServerThread(this, playersConnected);
				serverThreads[playersConnected] = st;
				playersConnected++;
				// If everyone is connected, start the threads.
				if(playersConnected == serverThreads.length){
					System.out.println("All connected");
					for(ServerThread s : serverThreads) {
						s.start();
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
	
	public ServerThread[] getServerThreads() {
		return serverThreads;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
}