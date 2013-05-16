package client;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Reads a file which properties can be loaded from.
 * @author Jonas Sjöberg
 * @version 1.0
 */

public class ReadConfig {
	
	Properties configFile;
	
	/**
	 * Tries to load the configFile.
	 */
	public ReadConfig() {
		configFile = new java.util.Properties();
		try {
			configFile.load(new FileInputStream("config.cfg"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the property matching the key.
	 * @param key The key to the value.
	 * @return The property matching the key, as a String.
	 */
	public String getProperty(String key) {
		String value = configFile.getProperty(key);
		return value;
	}
	
	/**
	 * Sets a property to a key.
	 * @param key The key to the value.
	 * @param value The value to be set to the key.
	 */
	public void setProperties(String key, String value) {
		configFile.setProperty(key, value);
	}
}