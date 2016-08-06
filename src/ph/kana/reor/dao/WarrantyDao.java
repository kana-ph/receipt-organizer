package ph.kana.reor.dao;

import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Warranty;

public interface WarrantyDao {
	Warranty findByIdAndDocument(Long id, Document document) throws DataAccessException;
}
