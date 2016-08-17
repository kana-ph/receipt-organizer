package ph.kana.reor.service;

import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.derby.DerbyCategoryDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Category;

public class DefaultCategoryService implements CategoryService {

	private final CategoryDao categoryDao = new DerbyCategoryDao();

	@Override
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
