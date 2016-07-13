package ph.kana.reor.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ph.kana.reor.util.DialogsUtil;

public class HomeController implements Initializable {

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO
	}

	@FXML
	public void addReceiptButtonClick() {
		DialogsUtil.openDialog(null, "Add Receipt", "ReceiptDetailDialog");
	}

}
