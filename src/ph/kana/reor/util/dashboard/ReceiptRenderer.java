package ph.kana.reor.util.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;
import ph.kana.reor.type.DashboardClass;

public class ReceiptRenderer extends DocumentRenderer<Receipt> {

	protected ReceiptRenderer(Receipt receipt) {
		super(receipt);
	}

	@Override
	public Pane buildDocumentPane() {
		Pane pane = createDocumentCard();
		List<Node> nodes = pane.getChildren();

		Label title = new Label(document.getTitle());
		nodes.add(title);
		addStyleClasses(title, DashboardClass.TITLE);
		assignAnchors(title, 15.0, null, null, 15.0);

		Label receiptDateKey = new Label("Receipt Date:");
		nodes.add(receiptDateKey);
		addStyleClasses(receiptDateKey, DashboardClass.FIELD_NAME);
		assignAnchors(receiptDateKey, 50.0, null, null, 15.0);

		Label receiptDateValue = new Label(formatDate(document.getDate()));
		nodes.add(receiptDateValue);
		assignAnchors(receiptDateValue, 120.0, null, null, 15.0);

		TextArea description = new TextArea(document.getDescription());
		nodes.add(description);
		description.setWrapText(true);
		description.setEditable(false);
		addStyleClasses(description, DashboardClass.DESCRIPTION_BOX);
		assignAnchors(description, 75.0, 15.0, 60.0, 5.0);

		Label warrantyKey = new Label("Warranty:");
		nodes.add(warrantyKey);
		addStyleClasses(warrantyKey, DashboardClass.FIELD_NAME);
		assignAnchors(warrantyKey, 150.0, null, null, 15.0);

		Map<String, String> warrantyData = collectWarrantyDisplayData(document.getWarranty().orElse(null));

		Label warrantyValue = new Label(warrantyData.get("expiration"));
		nodes.add(warrantyValue);
		assignAnchors(warrantyValue, 150.0, null, null, 100.0);

		String status = warrantyData.get("status");
		if (status != null) {
			Label warrantyStatus = new Label(status);
			nodes.add(warrantyStatus);
			addStyleClasses(warrantyStatus, status.equals("(ACTIVE)")? DashboardClass.WARRANTY_ACTIVE : DashboardClass.WARRANTY_INACTIVE);
			assignAnchors(warrantyStatus, 150.0, null, null, 195.0);
		}

		Label categoryKey = new Label("Category:");
		nodes.add(categoryKey);
		addStyleClasses(warrantyKey, DashboardClass.FIELD_NAME);
		assignAnchors(categoryKey, null, null, 10.0, 15.0);

		Label categoryValue = new Label(document.getCategory().getValue());
		nodes.add(categoryValue);
		addStyleClasses(categoryValue, DashboardClass.CATEGORY);
		assignAnchors(categoryValue, null, null, 10.0, 100.0);

		Label amount = new Label(formatAmount(document.getAmount()));
		nodes.add(amount);
		addStyleClasses(amount, DashboardClass.AMOUNT);
		assignAnchors(amount, 20.0, 15.0, null, null);

		renderControls(nodes);

		return pane;
	}

	private String formatAmount(BigDecimal amount) {
		return String.format("%c %.2f", '\u20b1', amount);
	}

	private Map<String, String> collectWarrantyDisplayData(Warranty warranty) {
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