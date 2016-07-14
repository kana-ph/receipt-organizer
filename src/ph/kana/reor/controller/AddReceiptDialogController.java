package ph.kana.reor.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class AddReceiptDialogController extends AbstractReceiptDialogController {

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);
		receiptDatePicker.setValue(LocalDate.now());
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
	}

	@FXML
	public void saveButtonClick() {

	}
}
