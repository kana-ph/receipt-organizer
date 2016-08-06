package ph.kana.reor.dao.derby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import ph.kana.reor.dao.AttachmentDao;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.WarrantyDao;
import ph.kana.reor.dao.transaction.Transaction;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Category;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;

public class DerbyReceiptDao extends Transaction<Receipt> implements ReceiptDao {
	private final AttachmentDao attachmentDao = new DerbyAttachmentDao();
	private final CategoryDao categoryDao = new DerbyCategoryDao();
	private final WarrantyDao warrantyDao = new DerbyWarrantyDao();

	@Override
	public Receipt map(ResultSet resultSet) throws DataAccessException {
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
}
