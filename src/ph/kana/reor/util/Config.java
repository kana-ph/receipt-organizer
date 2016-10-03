package ph.kana.reor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
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

	public String getValue() {  //TODO Change type to Optional
		return Optional
			.ofNullable((String) CONFIG.get(key))
			.orElse(null);
	}

	public void setValue(String value) {
		CONFIG.setProperty(key, value);
		storeConfig();
	}

	private static Properties loadConfig() {
		Properties config = new Properties();

		try (InputStream inputStream = new FileInputStream(fetchConfigFile())) {
			config.load(inputStream);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
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
			String comments = new StringBuilder()
				.append("Config file for receipt-organizer\n")
				.append("https://github.com/kana0011/receipt-organizer \n")
				.toString();
			CONFIG.store(outputStream, comments);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
