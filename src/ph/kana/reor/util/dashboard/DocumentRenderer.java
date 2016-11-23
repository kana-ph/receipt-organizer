package ph.kana.reor.util.dashboard;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.service.AttachmentService;
import ph.kana.reor.type.DashboardClass;

public abstract class DocumentRenderer<T extends Document> {

	public static DocumentRenderer getInstance(Stage window, Document document) {
		if (document instanceof Receipt) {
			return new ReceiptRenderer(window, (Receipt) document);
		} else {
			throw new IllegalArgumentException("Unknown Document type: " + document.getClass());
		}
	}

	protected final Stage window;
	protected final T document;

	private final AttachmentService attachmentService = new AttachmentService();

	public abstract Pane buildDocumentPane();

	protected DocumentRenderer(Stage window, T document) {
		this.window = window;
		this.document = document;
	}

	protected Pane createDocumentCard() {
		Pane pane = new AnchorPane();
		addStyleClasses(pane, DashboardClass.DOCUMENT_CARD);
		pane.setPrefHeight(200.0);
		pane.setMinHeight(200.0);

		return pane;
	}

	protected void renderControls(List<Node> parentNodes, LinkedHashMap<Label, Double> controls) {
		controls.keySet().stream()
			.peek(parentNodes::add)
			.forEach(label -> assignAnchors(label, null, 15.0, controls.get(label), null));
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

	protected int countAttachments() throws ServiceException {
		return attachmentService.countAllByDocument(document);
	}
}
