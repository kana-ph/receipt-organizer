package ph.kana.reor.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;
import ph.kana.reor.type.DashboardClass;
import static ph.kana.reor.type.DashboardClass.*;

public final class DashboardDocumentRenderer {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

	private DashboardDocumentRenderer() {}

	public static Pane buildDocumentPane(Document document) {
		if (document instanceof Receipt) {
			return buildReceiptPane((Receipt) document);
		}
		throw new IllegalArgumentException("Unknown Document type: " + document.getClass());
	}

	private static Pane buildReceiptPane(Receipt receipt) {
		Pane pane = new AnchorPane();
		pane.getStyleClass()
			.add(DOCUMENT_CARD.getName());
		List<Node> nodes = pane.getChildren();

		Label title = new Label(receipt.getTitle());
		nodes.add(title);
		addStyleClasses(title, TITLE);
		assignAnchors(title, 15.0, null, null, 15.0);

		Label receiptDateKey = new Label("Receipt Date:");
		nodes.add(receiptDateKey);
		addStyleClasses(receiptDateKey, FIELD_NAME);
		assignAnchors(receiptDateKey, 50.0, null, null, 15.0);

		Label receiptDateValue = new Label(formatDate(receipt.getDate()));
		nodes.add(receiptDateValue);
		assignAnchors(receiptDateValue, 120.0, null, null, 15.0);

		TextArea description = new TextArea(receipt.getDescription());
		nodes.add(description);
		description.setWrapText(true);
		description.setEditable(false);
		addStyleClasses(description, DESCRIPTION_BOX);
		assignAnchors(description, 75.0, 15.0, 60.0, 5.0);

		Label warrantyKey = new Label("Warranty:");
		nodes.add(warrantyKey);
		addStyleClasses(warrantyKey, FIELD_NAME);
		assignAnchors(warrantyKey, 150.0, null, null, 15.0);

		Map<String, String> warrantyData = collectWarrantyDisplayData(receipt.getWarranty().orElse(null));

		Label warrantyValue = new Label(warrantyData.get("expiration"));
		nodes.add(warrantyValue);
		assignAnchors(warrantyValue, 150.0, null, null, 100.0);

		String status = warrantyData.get("status");
		if (status != null) {
			Label warrantyStatus = new Label(status);
			nodes.add(warrantyStatus);
			addStyleClasses(warrantyStatus, status.equals("(ACTIVE)")? WARRANTY_ACTIVE : WARRANTY_INACTIVE);
			assignAnchors(warrantyValue, 150.0, null, null, 195.0);
		}

		Label categoryKey = new Label("Category:");
		nodes.add(categoryKey);
		addStyleClasses(warrantyKey, FIELD_NAME);
		assignAnchors(categoryKey, null, null, 10.0, 15.0);

		Label categoryValue = new Label(receipt.getCategory().getValue());
		nodes.add(categoryValue);
		addStyleClasses(categoryValue, CATEGORY);
		assignAnchors(categoryValue, null, null, 10.0, 100.0);

		Label amount = new Label(formatAmount(receipt.getAmount()));
		nodes.add(amount);
		addStyleClasses(amount, AMOUNT);
		assignAnchors(amount, 20.0, 15.0, null, null);

		Label optionsLink = new Label("Options");
		nodes.add(optionsLink);
		addStyleClasses(optionsLink, OPTIONS_LINK);
		assignAnchors(optionsLink, null, 15.0, 10.0, null);

		return pane;
	}

	private static void assignAnchors(Node node, Double top, Double right, Double bottom, Double left) {
		AnchorPane.setTopAnchor(node, top);
		AnchorPane.setRightAnchor(node, right);
		AnchorPane.setBottomAnchor(node, bottom);
		AnchorPane.setLeftAnchor(node, left);
	}

	private static void addStyleClasses(Node node, DashboardClass... dashboardClasses) {
		List<String> styleClasses = node.getStyleClass();
		Arrays.asList(dashboardClasses)
			.stream()
			.map(DashboardClass::getName)
			.forEachOrdered(styleClasses::add);
	}

	private static String formatDate(LocalDate date) {
		return date.format(DATE_FORMAT);
	}

	private static String formatAmount(BigDecimal amount) {
		BigDecimal scaledAmount = amount.setScale(2);
		return String.format("%c %f", '\u20b1', scaledAmount);
	}

	private static Map<String, String> collectWarrantyDisplayData(Warranty warranty) {
		Map<String, String> data = new HashMap<>(2);
		if (warranty != null) {
			LocalDate expirationDate = warranty
				.getExpiration()
				.orElse(null);
			if (expirationDate != null) {
				boolean expired = expirationDate
					.isAfter(LocalDate.now());
				data.put("expiration", formatDate(expirationDate));
				data.put("status", expired? "(EXPIRED)" : "(ACTIVE)");
			} else {
				data.put("expiration", "LIFETIME");
				data.put("status", "(ACTIVE)");
			}
		} else {
			data.put("expiration", "NONE");
			data.put("status", null);
		}
		return data;
	}
}
