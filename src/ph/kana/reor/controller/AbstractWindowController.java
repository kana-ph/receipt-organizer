package ph.kana.reor.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ph.kana.reor.type.MessageType;

public abstract class AbstractWindowController {

	abstract protected Pane getRootPane();

	protected Stage getWindow() {
		return (Stage) getRootPane().getScene().getWindow();
	}

	protected void showMessage(Label messageLabel, String message, MessageType type) {
		String formattedMessage = String.format("%c  %s", type.getIcon(), message);
		messageLabel.setText(formattedMessage);
		messageLabel.setTextFill(type.getPaint());

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), messageLabel);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.setCycleCount(1);
		fadeTransition.setAutoReverse(false);
		fadeTransition.play();
	}
}
