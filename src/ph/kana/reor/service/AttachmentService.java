package ph.kana.reor.service;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import ph.kana.reor.dao.AttachmentDao;
import ph.kana.reor.dao.derby.DerbyAttachmentDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;
import ph.kana.reor.util.FileUtil;

public class AttachmentService {

	private final AttachmentDao attachmentDao = new DerbyAttachmentDao();

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

	public Set<Attachment> fetchAllByDocument(Document document) throws ServiceException {
		try {
			return attachmentDao.findAllByDocument(document);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}

	public int countAllByDocument(Document document) throws ServiceException {
		try {
			return attachmentDao.countAllByDocument(document);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}
}
