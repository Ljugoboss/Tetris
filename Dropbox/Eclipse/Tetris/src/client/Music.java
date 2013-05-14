package client;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * @author Jonas Sjöberg
 * @version 1.0
 */
public class Music {
	URL url;
	AudioInputStream audioIn;
	Clip clip;
	FloatControl volume;

	@SuppressWarnings({ "deprecation", "static-access" })
	public Music(String filename) {
		File file = new File(filename);
		try {
			url = file.toURL();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try {
			audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		setVolume(3);
	}

	public void play() {
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

	public void restart() {
		clip.stop();
		clip.start();
	}
	
	/**
	 * Set the volume of the current clip
	 * @param The volume to be set
	 */
	public void setVolume(float v) {
		if (volume.getMinimum() <= v && v <= volume.getMaximum()) {
			volume.setValue(v);
		} else if (v < volume.getMinimum()) {
			volume.setValue(volume.getMinimum());
		} else {
			volume.setValue(volume.getMaximum());
		}
	}
}
