package ph.kana.reor.dao.derby;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import ph.kana.reor.dao.CategoryDao;
import ph.kana.reor.dao.transaction.Transaction;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Category;

public class DerbyCategoryDao extends Transaction<Category> implements CategoryDao {

	@Override
	public Category findByValue(String value) throws DataAccessException {
		String sql = "SELECT id, value FROM category WHERE value = ?";

		List<Category> results = executeQuery(connection -> {
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

		final String value = category.getValue();
		category = execute(category, connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, value);

			statement.executeUpdate();

			ResultSet idResultSet = statement.getGeneratedKeys();
			return idResultSet.getLong(1);
		});
		return category;
	}

	@Override
	public Category map(ResultSet resultSet) throws DataAccessException {
		try {
			Category category = new Category();
			category.setId(resultSet.getLong("id"));
			category.setValue(resultSet.getString("value"));
			return category;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
