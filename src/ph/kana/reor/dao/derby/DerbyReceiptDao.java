package ph.kana.reor.dao.derby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.transaction.Transaction;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Category;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;

public class DerbyReceiptDao extends Transaction<Receipt> implements ReceiptDao {

	@Override
	public Receipt map(ResultSet resultSet) throws SQLException {
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
	}

	private Set<Attachment> fetchAttachments(Receipt receipt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private Warranty fetchWarranty(Long warrantyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private Category fetchCategory(Long categoryId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
