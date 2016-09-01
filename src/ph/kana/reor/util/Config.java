package ph.kana.reor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public enum Config {

	STORAGE_DIR;

	private final String key;

	Config() {
		this.key = name()
			.toLowerCase()
			.replace("_", "-");
	}

	private final static Properties CONFIG = loadConfig();

	public String getValue() {
		return CONFIG.get(key).toString();
	}

	public void setValue(String value) {
		CONFIG.setProperty(key, value);
		storeConfig();
	}

	private static Properties loadConfig() {
		Properties config = new Properties();

		try (InputStream inputStream = new FileInputStream(fetchConfigFile())) {
			config.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		return config;
	}

	private static File fetchConfigFile() throws IOException {
		File configFile = new File(System.getProperty("user.home") + "/.reor-config");

		File dir = configFile.getParentFile();
		dir.mkdir();

		return configFile;
	}

	private static void storeConfig() {
		try (OutputStream outputStream = new FileOutputStream(fetchConfigFile())) {
			CONFIG.store(outputStream, "");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}