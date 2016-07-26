package ph.kana.reor.service;

import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Category;

public interface CategoryService {
	Category fetchCategory(String value) throws ServiceException;
}
