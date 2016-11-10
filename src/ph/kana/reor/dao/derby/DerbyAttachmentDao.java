package ph.kana.reor.dao.derby;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ph.kana.reor.dao.AttachmentDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;

public class DerbyAttachmentDao extends AttachmentDao {

	@Override
	public Set<Attachment> findAllByDocument(Document document) throws DataAccessException {
		if (document == null || document.getId() == null) {
			return new HashSet();
		} else {
			String sql = "SELECT id, path, file_name FROM attachment WHERE document_id = ?";
			List<Attachment> results = executeQuery(connection -> {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setLong(1, document.getId());
				return statement.executeQuery();
			});
			return new HashSet(results);
		}
	}

	@Override
	public Attachment save(Attachment attachment) throws DataAccessException {
		String sql = "INSERT INTO attachment(path, file_name, document_id) VALUES (?, ?, ?)";

		execute(attachment, connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);
			statement.setString(1, attachment.getPath());
			statement.setString(2, attachment.getFileName());
			statement.setLong(3, attachment.getDocument().getId());
			statement.executeUpdate();

			return fetchInsertId(statement);
		});
		return attachment;
	}

	@Override
	protected Attachment map(ResultSet resultSet) throws DataAccessException {
		try {
			Attachment attachment =  new Attachment();
			attachment.setId(resultSet.getLong("id"));
			attachment.setFileName(resultSet.getString("file_name"));
			attachment.setPath(resultSet.getString("path"));

			return attachment;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

}
