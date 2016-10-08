package ph.kana.reor.service;

import java.util.List;
import ph.kana.reor.dao.DocumentDao;
import ph.kana.reor.dao.derby.DerbyDocumentDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Document;

public class DocumentService {

	private final DocumentDao documentDao = new DerbyDocumentDao();

	public List<Document> fetchAll() throws ServiceException {
		try {
			return documentDao.fetchAll();
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}
}
