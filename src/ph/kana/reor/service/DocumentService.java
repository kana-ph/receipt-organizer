package ph.kana.reor.service;

import java.util.Collections;
import java.util.List;
import ph.kana.reor.dao.DocumentDao;
import ph.kana.reor.dao.derby.DerbyDocumentDao;
import ph.kana.reor.model.Document;

public class DocumentService {

	private final DocumentDao documentDao = new DerbyDocumentDao();

	public List<Document> fetchAll() {
		// TODO fetch all from each dao. Sort by date
		return Collections.EMPTY_LIST;
	}
}
