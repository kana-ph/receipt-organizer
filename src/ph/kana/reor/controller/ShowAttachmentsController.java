package ph.kana.reor.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ph.kana.reor.controller.common.AbstractWindowController;
import ph.kana.reor.controller.common.DocumentStatefulController;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;
import ph.kana.reor.util.DateUtil;
import ph.kana.reor.util.DesktopUtil;

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

		ImageView thumbnailImage = extractThumbnailImage(attachment);
		thumbnailLabel.setGraphic(thumbnailImage);

		EventHandler<MouseEvent> openFile = createOpenFileEvent(attachment);
		thumbnailLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, openFile);

		return thumbnailLabel;
	}

	private ImageView extractThumbnailImage(Attachment attachment) {
		File attachmentFile = new File(attachment.getPath());
		Image iconImage = DesktopUtil.extractFileThumbnail(attachmentFile);

		return new ImageView(iconImage);
	}

	private EventHandler<MouseEvent> createOpenFileEvent(Attachment attachment) {
		return mouseEvent -> {
			File imageFile = new File(attachment.getPath());
			DesktopUtil.openFile(imageFile);
		};
	}

}
