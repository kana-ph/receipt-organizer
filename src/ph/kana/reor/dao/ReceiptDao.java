package ph.kana.reor.dao;

import java.util.List;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Receipt;

public abstract class ReceiptDao extends AbstractDao<Receipt> {

	public abstract List<Receipt> fetchAll() throws DataAccessException;

	public abstract Receipt save(Receipt receipt) throws DataAccessException;
}
