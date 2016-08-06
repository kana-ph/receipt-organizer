package ph.kana.reor.dao;

import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Category;

public interface CategoryDao {
	Category findById(Long id) throws DataAccessException;

	Category findByValue(String value) throws DataAccessException;

	Category save(Category category) throws DataAccessException;
}
