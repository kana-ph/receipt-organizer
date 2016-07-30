package ph.kana.reor.dao.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {
	private ConnectionFactory() {}

	public static Connection openConnection() throws SQLException {
		String connectionString = "jdbc:derby:db/organizer;create=true"; // TODO externalize
		Connection connection = DriverManager.getConnection(connectionString);
		connection.setAutoCommit(false);

		return connection;
	}
}
