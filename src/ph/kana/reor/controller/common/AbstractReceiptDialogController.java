package ph.kana.reor.controller.common;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ph.kana.reor.type.MessageType;
import ph.kana.reor.util.DialogsUtil;
import ph.kana.reor.validator.ValidationRule;

public abstract class AbstractReceiptDialogController extends AbstractFormController {

	@FXML protected TextField titleTextField;
	@FXML protected TextField amountTextField;
	@FXML protected DatePicker receiptDatePicker;
	@FXML protected ListView<File> attachmentList;
	@FXML protected TextArea desciptionTextArea;
	@FXML protected CheckBox warrantyCheckbox;
	@FXML protected CheckBox lifetimeWarrantyCheckbox;
	@FXML protected DatePicker warrantyDatePicker;
	@FXML protected TextField tagsTextField;

	@FXML protected Label titleMessageLabel;
	@FXML protected Label amountMessageLabel;
	@FXML protected Label receiptDateMessageLabel;
	@FXML protected Label attachmentsMessageLabel;
	@FXML protected Label descriptionMessageLabel;
	@FXML protected Label warrantyMessageLabel;
	@FXML protected Label tagsMessageLabel;
	@FXML protected Label formMessageLabel;

	@FXML protected AnchorPane rootPane;
	@FXML protected HBox warrantyBox;

	@Override
	protected void initializeForm() {
		amountTextField.setTextFormatter(getBigDecimalTextFormatter());
	}

	@FXML
	public void addFileButtonClick() {
		List<File> attachments = DialogsUtil.showAttachmentsFileChooser(getWindow());
		addAttachments(attachments);
	}

	@FXML
	public void warrantyToggleClick() {
		boolean hasWarranty = warrantyCheckbox.isSelected();
		warrantyBox.setDisable(!hasWarranty);
	}

	@FXML
	public void lifetimeWarrantyToggleClick() {
		boolean lifetimeWarranty = lifetimeWarrantyCheckbox.isSelected();
		warrantyDatePicker.setDisable(lifetimeWarranty);
		if (lifetimeWarranty) {
			warrantyDatePicker.setValue(null);
		}
	}

	@FXML
	public void cancelButtonClick() {
		getWindow().close();
	}

	@Override
	protected List<ValidationRule> addErrorValidations() {
		List<ValidationRule> errorRules = new ArrayList();
		errorRules.add(new ValidationRule<TextField>(titleTextField, this::validateTitleError));
		errorRules.add(new ValidationRule<TextField>(amountTextField, this::validateAmountError));
		errorRules.add(new ValidationRule<DatePicker>(receiptDatePicker, this::validateReceiptDateError));
		errorRules.add(new ValidationRule<DatePicker>(warrantyDatePicker, this::validateWarrantyDateError));

		return errorRules;
	}

	@Override
	protected List<ValidationRule> addWarningValidations() {
		List<ValidationRule> warningRules = new ArrayList();
		warningRules.add(new ValidationRule<TextField>(amountTextField, this::validateAmountWarning));
		warningRules.add(new ValidationRule<ListView>(attachmentList, this::validateAttachmentsWarning));
		warningRules.add(new ValidationRule<TextField>(tagsTextField, this::validateTagsWarning));

		return warningRules;
	}

	private void addAttachments(List<File> attachments) {
		List<File> existingAttachments = attachmentList.getItems();
		attachments.forEach((File attachment) -> {
			if (!existingAttachments.contains(attachment)) {
				existingAttachments.add(attachment);
			}
		});
	}

	private boolean validateTitleError(TextField textField) {
		String title = textField.getText();
		if ((title != null) && !title.isEmpty()) {
			titleMessageLabel.setText("");
			return true;
		} else {
			showMessage(titleMessageLabel, "Title is required!", MessageType.ERROR);
			return false;
		}
	}

	private boolean validateAmountError(TextField textField) {
		String amount = textField.getText();

		if ((amount != null) && !amount.isEmpty()) {
			BigDecimal numericAmount = new BigDecimal(amount);
			if (numericAmount.compareTo(BigDecimal.ZERO) >= 0) {
				amountMessageLabel.setText("");
				return true;
			} else {
				showMessage(amountMessageLabel, "Amount cannot be negative!", MessageType.ERROR);
				return false;
			}
		} else {
			showMessage(amountMessageLabel, "Amount is required!", MessageType.ERROR);
			return false;
		}
	}

	private boolean validateAmountWarning(TextField textField) {
		BigDecimal amount = new BigDecimal(textField.getText());
		if (amount.equals(BigDecimal.ZERO)) {
			showMessage(amountMessageLabel, "Amount is zero?", MessageType.WARNING);
			return false;
		}
		return true;
	}

	private boolean validateReceiptDateError(DatePicker datePicker) {
		LocalDate date = datePicker.getValue();
		if (date == null) {
			showMessage(receiptDateMessageLabel, "Receipt date is required!", MessageType.ERROR);
			return false;
		}
		return true;
	}

	private boolean validateAttachmentsWarning(ListView listView) {
		List items = listView.getItems();
		if (items.isEmpty()) {
			showMessage(attachmentsMessageLabel, "No attached file?", MessageType.WARNING);
			return false;
		}
		return true;
	}

	private boolean validateWarrantyDateError(DatePicker datePicker) {
		if (datePicker.isDisabled()) {
			warrantyMessageLabel.setText("");
			return true;
		} else {
			showMessage(warrantyMessageLabel, "Warranty is required!", MessageType.ERROR);
			return false;
		}
	}

	private boolean validateTagsWarning(TextField textField) {
		String title = textField.getText();
		if ((title != null) && !title.isEmpty()) {
			tagsMessageLabel.setText("");
			return true;
		} else {
			showMessage(tagsMessageLabel, "No tags?", MessageType.WARNING);
			return false;
		}
	}
}
