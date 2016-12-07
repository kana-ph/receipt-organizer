package ph.kana.reor.dao.derby;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.WarrantyDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Category;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;

public class DerbyReceiptDao extends ReceiptDao {
	private final CategoryDao categoryDao = new DerbyCategoryDao();
	private final WarrantyDao warrantyDao = new DerbyWarrantyDao();

	@Override
	public List<Receipt> fetchAll() throws DataAccessException {
		String sql = "SELECT d.id, d.title, d.document_date, d.description, r.amount, r.category_id "
			+ "FROM document AS d INNER JOIN receipt AS r ON d.id = r.id ";
		return executeQuery(connection -> {
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);
		});
	}

	@Override
	public Receipt save(Receipt receipt) throws DataAccessException {
		return execute(receipt, connection -> {
			Long documentId = saveDocument(receipt, connection);
			return saveReceipt(receipt, documentId, connection);
		});
	}

	@Override
	protected Receipt map(ResultSet resultSet) throws DataAccessException {
		try {
			Receipt receipt = new Receipt();
			receipt.setId(resultSet.getLong("id"));
			receipt.setTitle(resultSet.getString("title"));
			receipt.setDate(resultSet.getDate("document_date").toLocalDate());
			receipt.setDescription(resultSet.getString("description"));
			receipt.setAmount(resultSet.getBigDecimal("amount"));
			receipt.setWarranty(fetchWarranty(receipt));
			receipt.setCategory(fetchCategory(resultSet.getLong("category_id")));

			return receipt;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private Warranty fetchWarranty(Receipt receipt) throws DataAccessException {
		Optional<Warranty> warranty = Optional
			.ofNullable(warrantyDao.findByDocument(receipt));
		warranty.ifPresent(w -> w.setDocument(receipt));
		return warranty
			.orElse(null);
	}

	private Category fetchCategory(Long categoryId) throws DataAccessException {
		return (categoryId == null)? null : categoryDao.findById(categoryId);
	}

	private Long saveDocument(Document document, Connection connection) throws SQLException {
		String sql = "INSERT INTO document(title, document_date, description) VALUES (?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);

		statement.setString(1, document.getTitle());
		statement.setDate(2, Date.valueOf(document.getDate()));
		statement.setString(3, document.getDescription());
		statement.executeUpdate();

		return fetchInsertId(statement);
	}

	private Long saveReceipt(Receipt receipt, Long documentId, Connection connection) throws SQLException {
		String sql = "INSERT INTO receipt(id, amount, category_id) VALUES (?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);

		statement.setLong(1, documentId);
		statement.setBigDecimal(2, receipt.getAmount());

		Category category = receipt.getCategory();
		if (category == null) {
			statement.setNull(3, Types.BIGINT);
		} else {
			statement.setLong(3, category.getId());
		}

		statement.executeUpdate();
		return fetchInsertId(statement);
	}
}
