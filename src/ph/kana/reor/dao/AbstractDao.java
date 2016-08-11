package ph.kana.reor.dao.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Model;
import ph.kana.reor.util.function.CheckedFunction;
import ph.kana.reor.util.function.CheckedRunnable;

public abstract class AbstractDao<T extends Model> {

	protected abstract T map(ResultSet resultSet) throws DataAccessException;

	public List<T> executeQuery(CheckedFunction<Connection, ResultSet> action) throws DataAccessException {
		return executeSqlStatement(true, connection -> {
			ResultSet resultSet = action.apply(connection);
			return mapToEntityList(resultSet);
		});
	}

	public T execute(T model, CheckedFunction<Connection, Long> action, boolean commit) throws DataAccessException {
		return executeSqlStatement(commit, connection -> {
			Long id = action.apply(connection);
			model.setId(id);
			return model;
		});
	}

	private <R> R executeSqlStatement(boolean commit, CheckedFunction<Connection, R> sqlFunction) throws DataAccessException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.openConnection();
			if (connection != null) {
				R rv = sqlFunction.apply(connection);

				if (commit) connection.commit();
				return rv;
			} else {
				throw new DataAccessException("Unable to open connection");
			}
		} catch (Exception e) {
			safeExecute(connection, connection::rollback);
			throw new DataAccessException(e);
		} finally {
			if (commit) safeExecute(connection, connection::close);
		}
	}

	private void safeExecute(Connection connection, CheckedRunnable runnable) throws DataAccessException {
		try {
			if (connection != null) {
				runnable.run();
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	private List<T> mapToEntityList(ResultSet resultSet) throws DataAccessException {
		List<T> mappedResult = new ArrayList();

		try {
			while (resultSet.next()) {
				mappedResult.add(map(resultSet));
			}

			return mappedResult;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
