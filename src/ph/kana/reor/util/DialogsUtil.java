package ph.kana.reor.util;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogsUtil {

	private static Stage primaryStage = null;

	public static void setPrimaryStage(Stage stage) {
		if (primaryStage == null) {
			primaryStage = stage;
		}
	}

	public static void openDialog(Stage parent, String title, String fxmlName) {
		String fxmlLocation = String.format("/ph/kana/reor/fxml/%s.fxml", fxmlName);

		try {
			URL fxmlResource = DialogsUtil.class.getResource(fxmlLocation);
			Parent root = FXMLLoader.load(fxmlResource);
			Scene scene = new Scene(root);

			Stage dialog = new Stage();
			dialog.initStyle(StageStyle.UNIFIED);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner((parent != null)? parent : primaryStage);

			dialog.setTitle(title);
			dialog.setScene(scene);
			dialog.sizeToScene();
			dialog.setResizable(false);

			dialog.show();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
