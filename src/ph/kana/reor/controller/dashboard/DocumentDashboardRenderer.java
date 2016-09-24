package ph.kana.reor.controller.dashboard;

import javafx.scene.layout.Pane;
import ph.kana.reor.model.Document;

public abstract class DocumentDashboardRenderer<T extends Document> {

	private final Document document;

	DocumentDashboardRenderer(T document) {
		this.document = document;
	}

	protected abstract Pane buildPaneForDocument(T document);

	public Pane render() {
		return buildPaneForDocument((T) document);
	}
}
