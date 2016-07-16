package ph.kana.reor.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ph.kana.reor.controller.common.AbstractReceiptDialogController;

public class AddReceiptDialogController extends AbstractReceiptDialogController {

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);
		clearForm();
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
	}

	@FXML
	public void saveButtonClick() {
		performSave(() -> {
			clearForm();
		});
	}

	private void clearForm() {
		titleTextField.setText("");
		amountTextField.setText("0");
		receiptDatePicker.setValue(LocalDate.now());
		attachmentList.getItems().clear();
		desciptionTextArea.setText("");
		warrantyCheckbox.setSelected(false);
		warrantyBox.setDisable(true);
		warrantyDatePicker.setValue(null);
		lifetimeWarrantyCheckbox.setSelected(false);
		tagsTextField.setText("");

		fieldValidity.put(titleTextField, false);
		fieldValidity.put(amountTextField, true);
		fieldValidity.put(receiptDatePicker, true);
		fieldValidity.put(warrantyDatePicker, true);
	}
}
