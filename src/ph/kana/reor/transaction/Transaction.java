package ph.kana.reor.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.util.function.DatabaseAction;
import ph.kana.reor.util.function.ThrowingRunnable;

public final class Transaction {

	private Transaction() {}

	public static void execute(DatabaseAction action) throws DataAccessException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.openConnection();
			if (connection != null) {
				action.run(connection);
				connection.commit();
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

	private static void safeExecute(Connection connection, ThrowingRunnable<SQLException> runnable) throws DataAccessException {
		try {
			if (connection != null) {
				runnable.runWithThrowable();
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
