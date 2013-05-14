package client;

import java.io.FileInputStream;
import java.util.Properties;
/**
 * 
 * @author Jonas Sj�berg
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
	 * @param key
	 * @return The property matching the key, as a String
	 */
	public String getProperty(String key) {
		String value = configFile.getProperty(key);
		return value;
	}
	
	public void setProperties(String key, String value) {
		configFile.setProperty(key, value);
	}
}