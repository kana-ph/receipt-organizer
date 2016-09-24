package ph.kana.reor.controller.dashboard;

import javafx.scene.layout.Pane;
import ph.kana.reor.model.Receipt;

public class ReceiptDashboardRenderer extends DocumentDashboardRenderer<Receipt> {

	public ReceiptDashboardRenderer(Receipt receipt) {
		super(receipt);
	}

	@Override
	protected Pane buildPaneForDocument(Receipt document) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
