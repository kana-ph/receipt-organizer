package ph.kana.reor.dao.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.util.function.DatabaseAction;
import ph.kana.reor.util.function.ThrowingRunnable;

public abstract class Transaction<T> {

	protected abstract T map(ResultSet resultSet) throws SQLException;

	public List<T> executeQuery(DatabaseAction action) throws DataAccessException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.openConnection();
			if (connection != null) {
				ResultSet resultSet = action.run(connection);
				connection.commit();

				return mapToEntityList(resultSet);
			} else {
				throw new DataAccessException("Unable to open connection");
			}
		} catch (SQLException e) {
			safeExecute(connection, connection::rollback);
			throw new DataAccessException(e);
		} finally {
			safeExecute(connection, connection::close);
		}
	}

	public T execute(T entity, DatabaseAction action) throws DataAccessException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.openConnection();
			if (connection != null) {

			} else {
				throw new DataAccessException("Unable to open connection");
			}
		} catch (SQLException e) {
			safeExecute(connection, connection::rollback);
			throw new DataAccessException(e);
		} finally {
			safeExecute(connection, connection::close);
		}
	}

	private void safeExecute(Connection connection, ThrowingRunnable<SQLException> runnable) throws DataAccessException {
		try {
			if (connection != null) {
				runnable.runWithThrowable();
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private List<T> mapToEntityList(ResultSet resultSet) throws SQLException {
		List<T> mappedResult = new ArrayList();

		while (resultSet.next()) {
			mappedResult.add(map(resultSet));
		}

		return mappedResult;
	}
}
