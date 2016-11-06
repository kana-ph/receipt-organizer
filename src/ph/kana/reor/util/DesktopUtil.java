package ph.kana.reor.util;

import com.sun.javafx.application.HostServicesDelegate;
import java.io.File;
import javafx.application.Application;

public final class DesktopUtil {

	private static DesktopUtil INSTANCE = null;

	public static void initialize(Application app) {
		if (INSTANCE == null) {
			INSTANCE = new DesktopUtil(app);
		}
	}

	public static DesktopUtil getInstance() {
		if (INSTANCE == null) {
			throw new UnsupportedOperationException("Uninitialized instance");
		}
		return INSTANCE;
	}

	private final HostServicesDelegate HOST;

	private DesktopUtil(Application app) {
		HOST = HostServicesDelegate.getInstance(app);
	}

	public void openFile(File file) {
		String path = file.getAbsolutePath();
		HOST.showDocument(path);
	}
}
