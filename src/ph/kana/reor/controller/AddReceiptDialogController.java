package ph.kana.reor.controller;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ph.kana.reor.controller.common.AbstractReceiptDialogController;

public class AddReceiptDialogController extends AbstractReceiptDialogController {

	@Override
	public void initializeForm() {
		clearForm();
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
	}

	@FXML
	public void saveButtonClick() {
		submit(() -> {
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
	}
}
