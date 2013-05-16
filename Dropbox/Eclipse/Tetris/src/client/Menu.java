package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.Server;

/**
 * Creates the start menu.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 *
 */
@SuppressWarnings("serial")
public class Menu extends JPanel implements ActionListener {
	private JButton play;
	private JButton host;
	private JButton join;
	private HiddenText textNumPlayers;
	private HiddenText textIP;
	private JButton ok;
	private JButton help;
	private JButton back;
	private Server server;
	private Tetris tetris;
	
	/**
	 * Initializes all the variables and sets the attributes we want.
	 * @param tetris The main class.
	 */
	public Menu(Tetris tetris) {
		this.tetris = tetris;
		play = new JButton("Play");
		host = new JButton("Host");
		join = new JButton("Join");
		help = new JButton("Help");
		back = new JButton("Back");
		ok = new JButton("OK");
		
		textNumPlayers = new HiddenText("Number of Players");
		textNumPlayers.setPreferredSize(new Dimension(200, 24));
		textIP = new HiddenText("IP-address");
		textIP.setPreferredSize(new Dimension(200, 24));
		
		this.add(play);
		this.add(host);
		this.add(help);
		
		play.addActionListener(this);
		host.addActionListener(this);
		join.addActionListener(this);
		help.addActionListener(this);
		back.addActionListener(this);
		ok.addActionListener(this);
		
		this.setOpaque(false);
		this.setVisible(true);
		updateUI();
	}
	
	/**
	 * Listens to the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == play) { // Play button: Clear the screen and sets up the next one.
			this.removeAll();
			this.add(textIP);
			this.add(join);
			JLabel info = new JLabel("<html><body>If you want to connect to your " +
					"own server or play <br> singleplayer, " +
					"type localhost or just press join.</body></html>");
			info.setForeground(Color.white);
			info.setOpaque(false);
			this.add(back);
			this.add(info);
			this.updateUI();
		} else if (e.getSource() == host) { // Host button: Clear the screen and sets up the next one.
			this.removeAll();
			this.add(textNumPlayers);
			this.add(ok);
			this.add(back);
			this.updateUI();
		} else if (e.getSource() == ok) { // OK button: Tries to parse the input and start a server. Takes you back to the main screen.
			try {
				int i = Integer.parseInt(textNumPlayers.getText());
				server = new Server(i);
				server.start();
			} catch (Exception ex) {
				System.out.println("You didn't type a number");
			}
			this.removeAll();
			this.add(play);
			this.add(host);
			this.add(back);
			this.updateUI();
		} else if (e.getSource() == join) { // Join button: Tries to get an IP-address from the input. Joins a server depending on input.
			try {
				String ip = textIP.getText();
				System.out.println(ip);
				if (ip.isEmpty() || ip.equalsIgnoreCase("localhost")) {
					tetris.setIP("localhost");
					if (server == null) {
						server = new Server(1);
						server.start();
					}
				} else {
					tetris.setIP(ip);
				}
				tetris.startGame();
			} catch (Exception ex) {
				System.out.println("You didn't type a number");
			}
		} else if(e.getSource() == help) { // Help button: Clear the screen and shows a label with information.
			removeAll();
			JLabel info = new JLabel("<html><body>" +
					"Move Left: Left arrow key" +
					"<br>Move Right: Right arrow key" +
					"<br>Rotate clockwise: Down arrow key" +
					"<br>Rotate counterclockwise: Up arrow key" +
					"<br>Move down: D" +
					"<br>Drop down: Space" +
					"<br>Pause: P</body></html>");
			info.setForeground(Color.white);
			this.add(info);
			this.add(back);
			this.updateUI();
		} else if(e.getSource() == back) { // Back button: Takes you back to the main screen.
			removeAll();
			this.add(play);
			this.add(host);
			this.add(help);
			this.updateUI();
		}
	}	
}
