package ph.kana.reor.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import ph.kana.reor.util.DialogsUtil;

public class HomeController implements Initializable {

	@FXML private Accordion toolbox;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		toolbox.setExpandedPane(toolbox.getPanes().get(0));
	}

	@FXML
	public void addReceiptButtonClick() {
		DialogsUtil.openDialog(null, "Add Receipt", "AddReceiptDialog");
	}

}
