package ph.kana.reor.util.dashboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

	protected void renderControls(List<Node> parentNodes, LinkedHashMap<String, EventHandler<MouseEvent>> controls) {
		List<String> keys = new ArrayList(controls.keySet());
		Collections.reverse(keys);

		double bottomAnchor = 10.0;
		for (String key : keys) {
			Label controlLink = new Label(key);
			parentNodes.add(controlLink);
			addStyleClasses(controlLink, DashboardClass.OPTION_LINK);
			assignAnchors(controlLink, null, 15.0, bottomAnchor, null);
			bottomAnchor += 20.0;

			EventHandler<MouseEvent> clickEvent = controls.get(key);
			controlLink.addEventFilter(MouseEvent.MOUSE_CLICKED, clickEvent);
		}
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
