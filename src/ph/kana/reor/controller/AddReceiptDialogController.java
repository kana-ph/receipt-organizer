package ph.kana.reor.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.converter.BigDecimalStringConverter;
import ph.kana.reor.util.DialogsUtil;

public class AddReceiptDialogController extends AbstractWindowController implements Initializable {

	@FXML private TextField titleTextField;
	@FXML private TextField amountTextField;
	@FXML private DatePicker receiptDatePicker;
	@FXML private ListView<File> attachmentList;
	@FXML private TextArea desciptionTextArea;
	@FXML private CheckBox warrantyCheckbox;
	@FXML private CheckBox lifetimeWarrantyCheckbox;
	@FXML private DatePicker warrantyDatePicker;
	@FXML private TextField tagsTextField;

	@FXML private AnchorPane rootPane;
	@FXML private HBox warrantyBox;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		receiptDatePicker.setValue(LocalDate.now());
		amountTextField.setTextFormatter(getBigDecimalTextFormatter());
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
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
}
