package ph.kana.reor.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ph.kana.reor.controller.common.AbstractWindowController;
import ph.kana.reor.controller.dashboard.DocumentDashboardRenderer;
import ph.kana.reor.controller.dashboard.DocumentDashboardRendererFactory;
import ph.kana.reor.model.Document;
import ph.kana.reor.service.DocumentService;
import ph.kana.reor.type.EmptyListMessage;
import ph.kana.reor.util.Config;
import ph.kana.reor.util.DialogsUtil;

public class HomeController extends AbstractWindowController implements Initializable {

	@FXML private Accordion toolbox;
	@FXML private Pane rootPane;

	@FXML private Pane viewPane;

	@FXML private Pane storageDirectoryPrompt;

	@FXML private Pane emptyListPane;
	@FXML private Label emptyListMessageLabel;

	private final DocumentService documentService = new DocumentService();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		toolbox.setExpandedPane(toolbox.getPanes().get(0));
		ensureStorageDirecotryConfig();

		asyncLoadDocuments();
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
	}

	@FXML
	public void addReceiptButtonClick() {
		DialogsUtil.openDialog(getWindow(), "Add Receipt", "AddReceiptDialog");
	}

	@FXML
	public void saveStorageDirClick() {
		Optional
			.ofNullable(DialogsUtil.showStorageDirectoryChooser(getWindow()))
			.ifPresent(directory -> {
				Config.STORAGE_DIR.setValue(directory.getAbsolutePath());
				storageDirectoryPrompt.setVisible(false);
			});
	}

	private void ensureStorageDirecotryConfig() {
		if (null == Config.STORAGE_DIR.getValue()) {
			storageDirectoryPrompt.setVisible(true);
		}
	}

	private void asyncLoadDocuments() {
		new Thread(() -> {
			List<Document> document = documentService.fetchAll();

			if (document.isEmpty()) {
				reportEmptyList(EmptyListMessage.NOTHING_YET);
			} else {
				emptyListPane.setVisible(false);
				document.stream()
					.forEachOrdered(this::renderDocument);
			}
		}).start();
	}

	private void renderDocument(Document document) {
		DocumentDashboardRenderer renderer =
			DocumentDashboardRendererFactory.buildInstance(document);
		Pane documentPane = renderer.render();
		viewPane.getChildren()
			.add(documentPane);
	}

	private void reportEmptyList(EmptyListMessage message) {
		emptyListMessageLabel.setText(message.getMessage());
		emptyListPane.setVisible(true);
	}
}
