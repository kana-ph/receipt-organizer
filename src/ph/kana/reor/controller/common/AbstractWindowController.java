package ph.kana.reor.controller.common;

import java.math.BigDecimal;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ph.kana.reor.type.MessageType;
import static ph.kana.reor.util.ValidationUtil.validatePositiveNumber;
import static ph.kana.reor.util.ValidationUtil.validateRequiredValue;

public abstract class AbstractWindowController {

	abstract protected Pane getRootPane();

	protected Stage getWindow() {
		return (Stage) getRootPane().getScene().getWindow();
	}

	protected boolean validateRequiredField(String fieldName, Control field, Label messageLabel) {
		Object value = getControlValue(field);
		boolean valid = validateRequiredValue(value);

		if (valid) {
			messageLabel.setText("");
		} else {
			showMessage(messageLabel, fieldName + " is required.", MessageType.ERROR);
		}
		return valid;
	}

	protected boolean validateNonNegativeNumberField(String fieldName, TextInputControl field, Label messageLabel) {
		BigDecimal value = new BigDecimal(field.getText());
		boolean valid = validatePositiveNumber(value);

		if (valid) {
			messageLabel.setText("");
		} else {
			showMessage(messageLabel, fieldName + " cannot have negative value.", MessageType.ERROR);
		}
		return valid;
	}

	protected void warnZeroNumberValue(String fieldName, TextInputControl field, Label messageLabel) {
		BigDecimal value = new BigDecimal(field.getText());

		if (BigDecimal.ZERO.equals(value)) {
			showMessage(messageLabel, fieldName + " has a zero value.", MessageType.WARNING);
		}
	}

	protected void warnEmptyList(String fieldName, ListView field, Label messageLabel) {
		List items = field.getItems();
		if (items.isEmpty()) {
			showMessage(messageLabel, fieldName + " has no items.", MessageType.WARNING);
		}
	}

	protected void warnEmptyField(String fieldName, Control field, Label messageLabel) {
		Object value = getControlValue(field);
		boolean valid = validateRequiredValue(value);

		if (!validateRequiredValue(value)) {
			showMessage(messageLabel, fieldName + " has no value.", MessageType.WARNING);
		}
	}

	private void showMessage(Label messageLabel, String message, MessageType type) {
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

	private Object getControlValue(Control control) {
		if (control instanceof TextInputControl) {
			return ((TextInputControl) control).getText();
		} else if (control instanceof ComboBoxBase) {
			return ((ComboBoxBase) control).getValue();
		} else {
			throw new IllegalArgumentException("Unable to get value of control with type: " + control.getClass());
		}
	}
}
