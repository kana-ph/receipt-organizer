package ph.kana.reor.dao;

import java.util.List;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Document;


public abstract class DocumentDao extends AbstractDao<Document> {

	public abstract List<Document> fetchAll() throws DataAccessException;

}
