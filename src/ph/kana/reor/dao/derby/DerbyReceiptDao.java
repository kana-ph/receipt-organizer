package ph.kana.reor.dao.derby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.transaction.Transaction;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Category;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;

public class DerbyReceiptDao extends Transaction<Receipt> implements ReceiptDao {

	private final CategoryDao categoryDao = new DerbyCategoryDao();

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
			receipt.setWarranty(fetchWarranty(resultSet.getLong("warranty_id")));
			receipt.setCategory(fetchCategory(resultSet.getLong("category_id")));

			return receipt;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private Set<Attachment> fetchAttachments(Receipt receipt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private Warranty fetchWarranty(Long warrantyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private Category fetchCategory(Long categoryId) throws DataAccessException {
		return categoryDao.findById(categoryId);
	}
}
