package ph.kana.reor.controller.common;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.BigDecimalStringConverter;
import ph.kana.reor.type.MessageType;
import ph.kana.reor.util.DialogsUtil;

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

	protected Map<Control, Boolean> fieldValidity = new HashMap();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		amountTextField.setTextFormatter(getBigDecimalTextFormatter());

		addFocusListener(titleTextField, this::titleTextFieldFocusChanged);
		addFocusListener(amountTextField, this::amountTextFieldFocusChanged);
		addFocusListener(receiptDatePicker, this::receiptDatePickerFocusChanged);
		addFocusListener(attachmentList, this::attachmentListFocusChanged);
		addFocusListener(warrantyDatePicker, this::warrantyDateFocusChanged);
		addFocusListener(tagsTextField, this::tagsTextFieldFocusChanged);

		addDisableListener(warrantyDatePicker, this::warrantyDateDisableChanged);
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

	protected void performSave(Runnable save) {
		if (formValid()) {
			save.run();
			showMessage(formMessageLabel, "Receipt Saved!", MessageType.SUCCESS);
		} else {
			formMessageLabel.setText("");
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

	private void addFocusListener(Control field, ChangeListener<Boolean> listener) {
		field.focusedProperty().addListener(listener);
	}

	private void addDisableListener(Control field, ChangeListener<Boolean> listener) {
		field.disabledProperty().addListener(listener);
	}

	private boolean checkRequiredFieldOnDefocus(boolean focused, String fieldName, Control field, Label messageLabel) {
		if (!focused) {
			return validateRequiredField(fieldName, field, messageLabel);
		} else {
			return fieldValidity.get(field);
		}
	}

	private void titleTextFieldFocusChanged(ObservableValue<? extends Boolean> focus, boolean oldFocus, boolean newFocus) {
		boolean valid = checkRequiredFieldOnDefocus(focus.getValue(), "Title", titleTextField, titleMessageLabel);
		fieldValidity.put(titleTextField, valid);
	}

	private void amountTextFieldFocusChanged(ObservableValue<? extends Boolean> focus, boolean oldFocus, boolean newFocus) {
		boolean focused = focus.getValue();
		if (!focused) {
			String fieldName = "Amount";
			boolean valid = true;

			valid &= validateRequiredField(fieldName, amountTextField, amountMessageLabel);
			valid &= validateNonNegativeNumberField(fieldName, amountTextField, amountMessageLabel);

			if (valid) {
				warnZeroNumberValue(fieldName, amountTextField, amountMessageLabel);
			}
			fieldValidity.put(amountTextField, valid);
		}
	}

	private void receiptDatePickerFocusChanged(ObservableValue<? extends Boolean> focus, boolean oldFocus, boolean newFocus) {
		boolean valid = checkRequiredFieldOnDefocus(focus.getValue(), "Receipt Date", receiptDatePicker, receiptDateMessageLabel);
		fieldValidity.put(receiptDatePicker, valid);
	}

	private void attachmentListFocusChanged(ObservableValue<? extends Boolean> focus, boolean oldFocus, boolean newFocus) {
		warnEmptyList("Attachement List", attachmentList, attachmentsMessageLabel);
	}

	private void warrantyDateFocusChanged(ObservableValue<? extends Boolean> focus, boolean oldFocus, boolean newFocus) {
		boolean valid = checkRequiredFieldOnDefocus(focus.getValue(), "Warranty Date", warrantyDatePicker, warrantyMessageLabel);
		fieldValidity.put(warrantyDatePicker, valid);
	}

	private void warrantyDateDisableChanged(ObservableValue<? extends Boolean> disable, boolean oldDisable, boolean newDisable) {
		warrantyMessageLabel.setText("");
		fieldValidity.put(warrantyDatePicker, true);
	}

	private void tagsTextFieldFocusChanged(ObservableValue<? extends Boolean> focus, boolean oldFocus, boolean newFocus) {
		warnEmptyField("Tags", tagsTextField, tagsMessageLabel);
	}

	private boolean formValid() {
		boolean valid = true;
		valid = fieldValidity.keySet()
			.stream()
			.map((field) ->  fieldValidity.get(field))
			.reduce(valid, (accumulator, _item) -> accumulator & _item);

		return valid;
	}
}
