package ph.kana.reor.dao.derby;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import ph.kana.reor.dao.AttachmentDao;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.WarrantyDao;
import ph.kana.reor.dao.common.AbstractDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Category;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;

public class DerbyReceiptDao extends AbstractDao<Receipt> implements ReceiptDao {
	private final AttachmentDao attachmentDao = new DerbyAttachmentDao();
	private final CategoryDao categoryDao = new DerbyCategoryDao();
	private final WarrantyDao warrantyDao = new DerbyWarrantyDao();

	@Override
	public Receipt save(Receipt receipt) {
		return execute(receipt, connection -> {
			Long documentId = saveDocument(receipt, connection);
			Long warrantyId = saveWarranty(receipt); // TODO move after-save receipt
		});
	}

	@Override
	protected Receipt map(ResultSet resultSet) throws DataAccessException {
		try {
			Receipt receipt = new Receipt();
			receipt.setId(resultSet.getLong("id"));
			receipt.setTitle(resultSet.getString("title"));
			receipt.setAmount(resultSet.getBigDecimal("amount"));
			receipt.setDate(resultSet.getDate("receipt_date").toLocalDate());
			receipt.setAttachments(fetchAttachments(receipt));
			receipt.setDescription(resultSet.getString("description"));
			receipt.setWarranty(fetchWarranty(receipt, resultSet.getLong("warranty_id")));
			receipt.setCategory(fetchCategory(resultSet.getLong("category_id")));

			return receipt;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private Set<Attachment> fetchAttachments(Receipt receipt) throws DataAccessException {
		Set<Attachment> attachments = attachmentDao.findAllByDocument(receipt);
		attachments.stream()
			.forEach(attachment -> attachment.setDocument(receipt));
		return attachments;
	}

	private Warranty fetchWarranty(Receipt receipt, Long warrantyId) throws DataAccessException {
		if (warrantyId !=  null) {
			Warranty warranty = warrantyDao.findByIdAndDocument(warrantyId, receipt);
			warranty.setDocument(receipt);
		}
		return null;
	}

	private Category fetchCategory(Long categoryId) throws DataAccessException {
		return (categoryId == null)? null : categoryDao.findById(categoryId);
	}

	private Long saveDocument(Document document, Connection connection) throws SQLException {
		String sql = "INSERT INTO document(title, date, description) VALUES (?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, document.getTitle());
		statement.setDate(2, Date.valueOf(document.getDate()));
		statement.setString(3, document.getDescription());
		statement.executeUpdate();

		ResultSet idResultSet = statement.getGeneratedKeys();
		return idResultSet.next()? idResultSet.getLong(1) : null;
	}

	private Long saveWarranty(Receipt receipt) throws DataAccessException {
		Warranty warranty = receipt.getWarranty();

		if (warranty != null) {
			warranty = warrantyDao.save(warranty);
			return warranty.getId();
		}
		return null;
	}
}
