package ph.kana.reor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.derby.DerbyReceiptDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Document;

public class DocumentService {

	private final ReceiptDao receiptDao = new DerbyReceiptDao();

	public List<Document> fetchAll() throws ServiceException {
		try {
			List<Document> documents = new ArrayList();
			documents.addAll(receiptDao.fetchAll());

			Collections.sort(documents, this::compareDatesDescending);
			return documents;
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}

	private int compareDatesDescending(Document a, Document b) {
		return b.getDate()
			.compareTo(a.getDate());
	}
}
