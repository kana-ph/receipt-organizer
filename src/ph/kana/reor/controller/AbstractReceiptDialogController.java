package ph.kana.reor.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.BigDecimalStringConverter;
import ph.kana.reor.exception.EmptyAttachmentException;
import ph.kana.reor.exception.ValidationException;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.type.MessageType;
import ph.kana.reor.util.DialogsUtil;
import static ph.kana.reor.util.ValidationUtil.validateHasAttachments;
import static ph.kana.reor.util.ValidationUtil.validatePositiveNumber;
import static ph.kana.reor.util.ValidationUtil.validateRequiredValue;

public abstract class AbstractReceiptDialogController extends AbstractWindowController implements Initializable {

	@FXML protected TextField titleTextField;
	@FXML protected TextField amountTextField;
	@FXML protected DatePicker receiptDatePicker;
	@FXML protected ListView<File> attachmentList;
	@FXML protected TextArea desciptionTextArea;
	@FXML protected CheckBox warrantyCheckbox;
	@FXML protected CheckBox lifetimeWarrantyCheckbox;
	@FXML protected DatePicker warrantyDatePicker;
	@FXML protected TextField tagsTextField;
	@FXML protected Label messageLabel;

	@FXML protected AnchorPane rootPane;
	@FXML protected HBox warrantyBox;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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

	protected void performSave(Supplier<Receipt> save) {
		try {
			messageLabel.setTooltip(null);
			validateRequiredValue("Title", titleTextField.getText());
			validateRequiredValue("Amount", amountTextField.getText());
			validatePositiveNumber("Amount", new BigDecimal(amountTextField.getText()), true);
			validateRequiredValue("Receipt Date", receiptDatePicker.getValue());

			if (warrantyCheckbox.isSelected() && !lifetimeWarrantyCheckbox.isSelected()) {
				validateRequiredValue("Warranty Date", warrantyDatePicker.getValue());
			}

			Receipt receipt = save.get();
			reportSave(receipt);
		} catch (ValidationException e) {
			showMessage(messageLabel, e.getMessage(), MessageType.ERROR);
		}
	}

	private TextFormatter<BigDecimal> getBigDecimalTextFormatter() {
		return new TextFormatter(new BigDecimalStringConverter(), BigDecimal.ZERO);
	}

	private void addAttachments(List<File> attachments) {
		List<File> existingAttachments = attachmentList.getItems();
		attachments.forEach((File attachment) -> {
			if (!existingAttachments.contains(attachment)) {
				existingAttachments.add(attachment);
			}
		});
	}

	private void reportSave(Receipt receipt) {
		Set attachments = receipt.getAttachments();
		Set tags = receipt.getTags();
		BigDecimal amount = receipt.getAmount();

		List<String> warnings = new ArrayList();

		if (amount.equals(BigDecimal.ZERO)) {
			warnings.add("Amount is 0.00");
		}
		if (tags.isEmpty()) {
			warnings.add("No tags added");
		}
		try {
			validateHasAttachments(attachments);
		} catch (EmptyAttachmentException e) {
			warnings.add("No attachment added");
		}

		if (warnings.isEmpty()) {
			showMessage(messageLabel, "Receipt successfully saved!", MessageType.SUCCESS);
		} else {
			String warningDetails = String.join("\n", warnings);
			String warningMessage = "Receipt successfully saved with warnings!";
			showMessage(messageLabel, warningMessage, MessageType.WARNING);
			messageLabel.setTooltip(new Tooltip(warningDetails));
		}
	}
}
