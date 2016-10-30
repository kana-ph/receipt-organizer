package ph.kana.reor.util.dashboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.type.DashboardClass;

public abstract class DocumentRenderer<T extends Document> {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

	public static DocumentRenderer getInstance(Document document) {
		if (document instanceof Receipt) {
			return new ReceiptRenderer((Receipt) document);
		} else {
			throw new IllegalArgumentException("Unknown Document type: " + document.getClass());
		}
	}

	protected final T document;

	public abstract Pane buildDocumentPane();

	protected DocumentRenderer(T document) {
		this.document = document;
	}

	protected Pane createDocumentCard() {
		Pane pane = new AnchorPane();
		addStyleClasses(pane, DashboardClass.DOCUMENT_CARD);
		pane.setPrefHeight(200.0);
		pane.setMinHeight(200.0);

		return pane;
	}

	protected void renderControls(List<Node> parentNodes) {
		Label detailsLink = new Label("Details");
		parentNodes.add(detailsLink);
		addStyleClasses(detailsLink, DashboardClass.OPTION_LINK);
		assignAnchors(detailsLink, null, 15.0, 10.0, null);
	}

	protected void assignAnchors(Node node, Double top, Double right, Double bottom, Double left) {
		AnchorPane.setTopAnchor(node, top);
		AnchorPane.setRightAnchor(node, right);
		AnchorPane.setBottomAnchor(node, bottom);
		AnchorPane.setLeftAnchor(node, left);
	}

	protected void addStyleClasses(Node node, DashboardClass... dashboardClasses) {
		List<String> styleClasses = node.getStyleClass();
		Arrays.asList(dashboardClasses)
			.stream()
			.map(DashboardClass::getName)
			.forEachOrdered(styleClasses::add);
	}

	protected String formatDate(LocalDate date) {
		return date.format(DATE_FORMAT);
	}
}
