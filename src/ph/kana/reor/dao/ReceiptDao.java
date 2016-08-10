package ph.kana.reor.dao;

import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Receipt;

public interface ReceiptDao {
	Receipt save(Receipt receipt) throws DataAccessException;
}
