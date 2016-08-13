package ph.kana.reor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

	private ConnectionManager() {}

	public static Connection openConnection() throws SQLException {
		String connectionString = "jdbc:derby:db/organizer;create=true"; // TODO externalize
		return DriverManager.getConnection(connectionString);
	}
}
