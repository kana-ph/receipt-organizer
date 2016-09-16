package ph.kana.reor.dao;

import java.util.List;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Category;

public abstract class CategoryDao extends AbstractDao<Category> {
	public abstract List<Category> all() throws DataAccessException;

	public abstract Category findById(Long id) throws DataAccessException;

	public abstract Category findByValue(String value) throws DataAccessException;

	public abstract Category save(Category category) throws DataAccessException;
}
