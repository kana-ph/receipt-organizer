package ph.kana.reor.controller.common;

import java.io.File;
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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected List<ValidationRule> addWarningValidations() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void addAttachments(List<File> attachments) {
		List<File> existingAttachments = attachmentList.getItems();
		attachments.forEach((File attachment) -> {
			if (!existingAttachments.contains(attachment)) {
				existingAttachments.add(attachment);
			}
		});
	}
}
