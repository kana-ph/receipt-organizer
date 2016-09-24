package ph.kana.reor.util;

import javafx.scene.layout.Pane;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;

public final class DashboardDocumentRenderer {

	private DashboardDocumentRenderer() {}

	public static Pane buildDocumentPane(Document document) {
		if (document instanceof Receipt) {
			return buildReceiptPane((Receipt) document);
		}
		throw new IllegalArgumentException("Unknown Document type: " + document.getClass());
	}

	private static Pane buildReceiptPane(Receipt receipt) {
		return null;
	}
}
