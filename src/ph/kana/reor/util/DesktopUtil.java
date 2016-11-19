package ph.kana.reor.util;

import com.sun.javafx.application.HostServicesDelegate;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.image.Image;
import static ph.kana.reor.util.FileUtil.ACCEPTED_DOCS;
import static ph.kana.reor.util.FileUtil.ACCEPTED_IMAGES;

public final class DesktopUtil {

	private static DesktopUtil INSTANCE = null;

	public static void initialize(Application app) {
		if (INSTANCE == null) {
			INSTANCE = new DesktopUtil(app);
		}
	}

	public static void openFile(File file) throws IOException {
		if (Desktop.isDesktopSupported()) {
			new Thread(() -> {
				try {
					Desktop desktop = Desktop.getDesktop();
					desktop.open(file);
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}
			}).start();
		}
	}

	public static Image extractFileThumbnail(File file) { // TODO actually extract thumbnails
		String fileName = file.getName();
		int extensionIndex = fileName.lastIndexOf('.') + 1;
		String extension = fileName.substring(extensionIndex);

		String imagePath;
		if (ACCEPTED_IMAGES.contains(extension)) {
			imagePath = "/ph/kana/reor/fxml/assets/image.png";
		} else if (ACCEPTED_DOCS.contains(extension)) {
			imagePath = "/ph/kana/reor/fxml/assets/document.png";
		} else {
			imagePath = "/ph/kana/reor/fxml/assets/file.png";
		}

		InputStream imageStream = DesktopUtil.class.getResourceAsStream(imagePath);
		return new Image(imageStream);
	}

	private final HostServicesDelegate hostService;

	private DesktopUtil(Application app) {
		hostService = HostServicesDelegate.getInstance(app);
	}
}
