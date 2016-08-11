package ph.kana.reor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class ConnectionFactory {
	private ConnectionFactory() {}

	public static Connection openConnection() throws SQLException {
		String connectionString = "jdbc:derby:db/organizer;create=true"; // TODO externalize
		return DriverManager.getConnection(connectionString);
	}
}
