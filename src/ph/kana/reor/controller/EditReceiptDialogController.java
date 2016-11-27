package ph.kana.reor.controller;

import javafx.fxml.FXML;
import ph.kana.reor.controller.common.AbstractReceiptDialogController;
import ph.kana.reor.controller.common.DocumentStatefulController;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.type.MessageType;

public class EditReceiptDialogController
	extends AbstractReceiptDialogController implements DocumentStatefulController<Receipt> {

	@Override
	public void accept(Receipt document) {
		titleTextField.setText(document.getTitle());
		amountTextField.setText(document.getAmount().toString());
		receiptDatePicker.setValue(document.getDate());
		descriptionTextArea.setText(document.getDescription());

	}

	@FXML
	public void saveButtonClick() {
		save(() -> {
			updateReceipt();

			clearMessages();
			showMessage(formMessageLabel, "Successfully Updated!", MessageType.SUCCESS);
		});
	}

	private void updateReceipt() {

	}

}
