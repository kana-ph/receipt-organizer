package ph.kana.reor.controller;

import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ph.kana.reor.controller.common.AbstractWindowController;
import ph.kana.reor.controller.common.DocumentStatefulController;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;
import ph.kana.reor.util.DateUtil;

public class ShowAttachmentsController extends AbstractWindowController implements DocumentStatefulController<Document> {

	@FXML private Pane rootPane;

	@FXML private Label titleLabel;
	@FXML private Label dateLabel;

	@FXML private Pane thumbnailPane;

	@FXML
	public void closeDialog() {

	}

	@Override
	public void accept(Document document) {
		titleLabel.setText(document.getTitle());
		dateLabel.setText(DateUtil.format(document.getDate()));

		List<Attachment> attachments = Collections.EMPTY_LIST; // TODO query atachments of document

		attachments.stream()
			.map(this::createThumbnail)
			.forEachOrdered(thumbnailPane.getChildren()::add);
	}

	@Override
	protected Pane getRootPane() {
		return rootPane;
	}

	private Label createThumbnail(Attachment attachment) {
		Label thumbnailLabel = new Label(attachment.getFileName());
		thumbnailLabel.setGraphic(extractThumbnail(attachment));
		addOpenFileEvent(thumbnailLabel, attachment);
		return thumbnailLabel;
	}

	private ImageView extractThumbnail(Attachment attachment) {
		return null;
	}

	private void addOpenFileEvent(Label label, Attachment attachment) {

	}

}
