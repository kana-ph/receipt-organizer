package ph.kana.reor.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import ph.kana.reor.util.function.DatabaseAction;

public final class Transaction {

	private Transaction() {}

	public static void execute(DatabaseAction action) throws SQLException {
		Connection connection = null;
		try {
			connection = start();
			if (connection != null) {
				action.run(connection);
				connection.commit();
			} else {
				throw new SQLException("Unable to open connection");
			}
		} catch (SQLException e) {
			connection.rollback();
		} finally {
			connection.close();
		}
	}

	private static Connection start() throws SQLException {
		return ConnectionFactory.openConnection();
	}
}
