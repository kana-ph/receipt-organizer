package ph.kana.reor.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ph.kana.reor.type.MessageType;

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
		performSave(() -> {
			showMessage(messageLabel, "Successfully Saved!", MessageType.SUCCESS);
			clearForm();
		});
	}

	private void clearForm() {
		titleTextField.setText("");
		amountTextField.setText("0");
		receiptDatePicker.setValue(null);
		attachmentList.getItems().clear();
		desciptionTextArea.setText("");
		warrantyCheckbox.setSelected(false);
		warrantyBox.setDisable(true);
		warrantyDatePicker.setValue(null);
		lifetimeWarrantyCheckbox.setSelected(false);
		tagsTextField.setText("");
	}
}
