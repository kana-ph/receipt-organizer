package ph.kana.reor.util;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import static ph.kana.reor.type.DashboardClass.*;

public final class DashboardDocumentRenderer {

	private DashboardDocumentRenderer() {}

	public static Pane buildDocumentPane(Document document) {
		Pane documentPane = buildBaseDocumentPane(document);

		if (document instanceof Receipt) {
			documentPane = buildReceiptPane((Receipt) document, documentPane);
		}
		return documentPane;
	}

	private static Pane buildBaseDocumentPane(Document document) {
		Pane pane = new AnchorPane();
		pane.getStyleClass()
			.add(DOCUMENT_CARD.getName());


		return pane;
	}

	private static Pane buildReceiptPane(Receipt receipt, Pane basePane) {
		return basePane;
	}
}
