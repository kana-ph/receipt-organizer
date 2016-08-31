package ph.kana.reor.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Random;

public class FileUtil {

	private final static char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

	public static File upload(File file) {
		File target = createTagetFile(file);

		try {
			Files.copy(file.toPath(), target.toPath());
			return target;
		} catch (IOException e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	private static File createTagetFile(File file) {
		String storageDir = Config.get("storage-dir");
		String subDir = hashName(2);
		String name = hashName(4);
		String extension = fetchExtension(file.getName());

		String filename = String.format("%s/%s/%s%s", storageDir, subDir, name, extension);
		return new File(filename);
	}

	private static String hashName(int hexDigits) {
		Random rng = new SecureRandom();

		int byteLength = hexDigits / 2;
		byte[] out = new byte[byteLength];
		rng.nextBytes(out);
		return toHex(out);
	}

	private static String toHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	private static String fetchExtension(String filename) {
		int i = filename.lastIndexOf(".");

		if (i > 0) {
			return filename.substring(i);
		} else {
			return "";
		}
	}
}
