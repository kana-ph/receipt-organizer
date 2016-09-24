package ph.kana.reor.controller.dashboard;

import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;

public final class DocumentDashboardRendererFactory {

	private DocumentDashboardRendererFactory() {}

	public static DocumentDashboardRenderer buildInstance(Document document) {
		if (document instanceof Receipt) {
			return new ReceiptDashboardRenderer((Receipt) document);
		}
		return null;
	}
}
