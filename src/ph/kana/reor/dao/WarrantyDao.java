package ph.kana.reor.dao;

import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Warranty;

public abstract class WarrantyDao extends AbstractDao<Warranty> {

	public abstract Warranty findByIdAndDocument(Long id, Document document) throws DataAccessException;

	public abstract Warranty save(Warranty warranty) throws DataAccessException;
}
