package ph.kana.reor.dao.derby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import ph.kana.reor.dao.DocumentDao;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Document;

public class DerbyDocumentDao extends DocumentDao {

	private final ReceiptDao receiptDao = new DerbyReceiptDao();

	@Override
	public List<Document> fetchAll() throws DataAccessException {
		String sql = "SELECT id, title, document_date, description FROM document";
		return executeQuery(connection -> {
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);
		});
	}

	@Override
	protected Document map(ResultSet resultSet) throws DataAccessException {
		Document documentStub = new Document(){};
		try {
			documentStub.setId(resultSet.getLong("id"));
			documentStub.setTitle(resultSet.getString("title"));
			documentStub.setDate(resultSet.getDate("document_date").toLocalDate());
			documentStub.setDescription(resultSet.getString("description"));
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return materializeDocument(documentStub);
	}

	private Document materializeDocument(Document document) throws DataAccessException {
		Document concreteDocument;
		concreteDocument = receiptDao.fetchWithStub(document);

		if (concreteDocument == null) {
			throw new DataAccessException("Found abstract document: " + document.getId());
		}
		return concreteDocument;
	}

}
