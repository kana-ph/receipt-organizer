package ph.kana.reor.validator;

import javafx.scene.control.Control;
import javafx.scene.control.Label;

public class FieldInfo {
	private final String name;
	private final Control field;
	private final Label messageLabel;

	public FieldInfo(String name, Control field, Label messageLabel) {
		this.name = name;
		this.field = field;
		this.messageLabel = messageLabel;
	}

	public String getName() {
		return name;
	}

	public Control getField() {
		return field;
	}

	public Label getMessageLabel() {
		return messageLabel;
	}
}
