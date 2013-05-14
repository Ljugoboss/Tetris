package client;





/**
 * The main class wich only use is to start a new game. 
 * @author Hugo Nissar
 * @author Jonas Sj�berg
 * @version 1.0
 */

@SuppressWarnings("serial")
public class Tetris extends Frame{
	private ReadConfig readConfig = new ReadConfig();
	private String ip = readConfig.getProperty("ip");
	private int port = Integer.parseInt(readConfig.getProperty("port"));
	private String background = readConfig.getProperty("background");
	private String sound = readConfig.getProperty("music");
	private Client client;
	private Music music;
	private WindowListener wl;
	private PlayingPanel pp;
	private SpectatePanel sp;
	private Menu menu;

	private Panel panel = new Panel(background);

	public Tetris() {
		wl = new WindowListener(this);
		menu = new Menu(this);
		this.add(panel);
		panel.add(menu);
		panel.updateUI();
		music = new Music(sound);
		music.play();
		this.addWindowListener(wl);
	}

	public int getPort() {
		return port;
	}

	public String getIP () {
		return ip;
	}

	public static void main(String [] arg) {
		new Tetris();
	}

	public SpectatePanel getSP() {
		return sp;
	}

	public void startGame() {
		panel.removeAll();	
		pp = new PlayingPanel();
		client = new Client(this, pp.getPaint());	
		panel.addKeyListener(new Input(client));
		sp = new SpectatePanel(client.getPlayers());
		client.setSP(sp);

		panel.add(pp);
		panel.add(sp);
		panel.updateUI();
	}
	
	public void setIP(String ip) {
		this.ip = ip;
		readConfig.setProperties("ip", ip);
	}
	
	public Client getClient() {
		return client;
	}
}
