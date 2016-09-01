package ph.kana.reor.dao;

import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Receipt;

public abstract class ReceiptDao extends AbstractDao<Receipt> {
	public abstract Receipt save(Receipt receipt) throws DataAccessException;
}