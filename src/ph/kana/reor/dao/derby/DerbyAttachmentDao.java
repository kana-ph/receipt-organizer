package ph.kana.reor.dao.derby;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ph.kana.reor.dao.AttachmentDao;
import ph.kana.reor.dao.transaction.Transaction;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;

public class DerbyAttachmentDao extends Transaction<Attachment> implements AttachmentDao {

	@Override
	public Set<Attachment> findAllByDocument(Document document) throws DataAccessException {
		if (document == null || document.getId() == null) {
			return new HashSet();
		} else {
			String sql = "SELECT id, path FROM attachment WHERE document_id = ?";
			List<Attachment> results = executeQuery(connection -> {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setLong(1, document.getId());
				return statement.executeQuery();
			});
			return new HashSet(results);
		}
	}

	@Override
	protected Attachment map(ResultSet resultSet) throws DataAccessException {
		try {
			Attachment attachment =  new Attachment();
			attachment.setId(resultSet.getLong("id"));
			attachment.setPath(resultSet.getString("path"));

			return attachment;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

}
