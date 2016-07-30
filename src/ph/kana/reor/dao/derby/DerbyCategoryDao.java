package ph.kana.reor.dao.derby;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.transaction.Transaction;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Category;

public class DerbyCategoryDao extends Transaction<Category> implements CategoryDao {

	@Override
	public Category findByValue(String value) throws DataAccessException {
		String sql = "SELECT id, value FROM category WHERE value = ?";

		List<Category> results = execute(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, value);

			return statement.executeQuery();
		});

		if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

	@Override
	public Category save(Category category) throws DataAccessException {
		String sql = "INSERT INTO category(value) VALUES ?";

		List<Category> results = execute(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, category.getValue());

			statement.executeUpdate();
			// TODO fix save
			return null;
		});
		return null;
	}

	@Override
	public Category map(ResultSet resultSet) throws SQLException {
		Category category = new Category();
		category.setId(resultSet.getLong("id"));
		category.setValue(resultSet.getString("value"));
		return category;
	}

}
