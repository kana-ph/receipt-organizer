package ph.kana.reor.controller;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class AbstractWindowController {

	abstract protected Pane getRootPane();

	protected Stage getWindow() {
		return (Stage) getRootPane().getScene().getWindow();
	}
}
