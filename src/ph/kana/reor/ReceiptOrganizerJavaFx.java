package ph.kana.reor;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ph.kana.reor.util.ConnectionManager;
import ph.kana.reor.util.DesktopUtil;

public class ReceiptOrganizerJavaFx extends Application {

	private static final double APP_WIDTH = 720.00;
	private static final double APP_HEIGHT = 600.0;

	public static void main(String[] args) {
		ConnectionManager.prepareDatabase();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setupStage(stage);
		DesktopUtil.initialize(this);
	}

	private void setupStage(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ph/kana/reor/fxml/Home.fxml"));

		Scene scene = new Scene(root);

		setUserAgentStylesheet(STYLESHEET_MODENA);

		stage.setWidth(APP_WIDTH);
		stage.setMinWidth(APP_WIDTH);

		stage.setHeight(APP_HEIGHT);
		stage.setMinHeight(APP_HEIGHT);

		stage.setTitle("kana0011/receipt-organizer");

		stage.setScene(scene);
		stage.show();
	}
}
