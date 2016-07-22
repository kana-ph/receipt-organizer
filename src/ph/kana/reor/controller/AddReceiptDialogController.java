package ph.kana.reor.controller;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ph.kana.reor.controller.common.AbstractReceiptDialogController;
import ph.kana.reor.type.MessageType;

public class AddReceiptDialogController extends AbstractReceiptDialogController {

	@Override
	public void initializeForm() {
		super.initializeForm();
		clearForm();
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
	}

	@FXML
	public void saveButtonClick() {
		save(() -> {
			clearForm();
			clearMessages();
			showMessage(formMessageLabel, "Successfully Saved!", MessageType.SUCCESS);
		});
	}

	private void clearForm() {
		titleTextField.setText("");
		amountTextField.setText("0");
		receiptDatePicker.setValue(LocalDate.now());
		attachmentList.getItems().clear();
		descriptionTextArea.setText("");
		warrantyCheckbox.setSelected(false);
		warrantyBox.setDisable(true);
		warrantyDatePicker.setValue(null);
		lifetimeWarrantyCheckbox.setSelected(false);
		tagsTextField.setText("");
	}
}
