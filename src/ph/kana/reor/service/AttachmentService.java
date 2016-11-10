package ph.kana.reor.service;

import java.io.File;
import java.io.IOException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.util.FileUtil;

public class AttachmentService {

	public Attachment buildInstance(File file) throws ServiceException {
		try {
			File uploadedFile = FileUtil.upload(file);

			Attachment attachment = new Attachment();
			attachment.setFileName(file.getName());
			attachment.setPath(uploadedFile.getPath());

			return attachment;
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}
}
