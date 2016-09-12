package ph.kana.reor.service;

import java.util.Set;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.derby.DerbyCategoryDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Category;

public class CategoryService {

	private final CategoryDao categoryDao = new DerbyCategoryDao();

	public Set<Category> fetchAllCategories() throws ServiceException {
		try {
			return categoryDao.findAll();
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}

	public Category fetchCategory(String value) throws ServiceException {
		if (value == null) {
			return null;
		}

		try {
			Category category = categoryDao.findByValue(value);

			if (category == null) {
				category = new Category();
				category.setValue(value);

				category = categoryDao.save(category);
			}
			return category;
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}
}
