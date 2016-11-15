package ph.kana.reor.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ph.kana.reor.controller.common.DocumentStatefulController;
import ph.kana.reor.model.Document;

public class DialogsUtil {

	public static void openDialog(Stage parent, String title, String fxmlName, Document document) {
		try {
			URL fxmlResource = fetchFxmlResource(fxmlName);
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlResource);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent root = (Parent) fxmlLoader.load(fxmlResource.openStream());

			Stage dialog = createDialog(root, parent, title);

			DocumentStatefulController controller = (DocumentStatefulController) fxmlLoader.getController();
			controller.accept(document);

			dialog.show();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public static void openDialog(Stage parent, String title, String fxmlName) {
		try {
			URL fxmlResource = fetchFxmlResource(fxmlName);
			createDialog(FXMLLoader.load(fxmlResource), parent, title)
				.show();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public static List<File> showAttachmentsFileChooser(Stage parent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Attachments");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(fetchValidAttachmentFilter());

		List<File> files = fileChooser.showOpenMultipleDialog(parent);
		return Optional
			.ofNullable(files)
			.orElse(Collections.EMPTY_LIST);
	}

	public static File showStorageDirectoryChooser(Stage parent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Storage Location");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Pictures"));

		return directoryChooser.showDialog(parent);
	}

	private static URL fetchFxmlResource(String fxmlName) {
		String fxmlLocation = String.format("/ph/kana/reor/fxml/%s.fxml", fxmlName);
		return DialogsUtil.class.getResource(fxmlLocation);
	}

	private static Stage createDialog(Parent root, Stage parentWindow, String title) {
		Scene scene = new Scene(root);

		Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UNIFIED);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(parentWindow);

		dialog.setTitle(title);
		dialog.setScene(scene);
		dialog.sizeToScene();
		dialog.setResizable(false);

		return dialog;
	}

	private static List<FileChooser.ExtensionFilter> fetchValidAttachmentFilter() {
		FileChooser.ExtensionFilter[] filters = {
			new FileChooser.ExtensionFilter("All Accepted Types", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.pdf", "*.doc", "*.docx", "*.xls", "*.xlsx", "*.odt", "*.rtf" ),
			new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif"),
			new FileChooser.ExtensionFilter("Document Files", "*.pdf", "*.doc", "*.docx", "*.xls", "*.xlsx", "*.odt", "*.rtf")
		};
		return Arrays.asList(filters);
	}
}
