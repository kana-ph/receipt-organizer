package ph.kana.reor.dao.derby;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import ph.kana.reor.dao.DocumentDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Document;

public class DerbyDocumentDao extends DocumentDao {

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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
