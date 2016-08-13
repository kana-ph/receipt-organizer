package ph.kana.reor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

	private ConnectionManager() {}

	private static final String CONNECTION_STRING = "jdbc:derby:db/organizer;create=true"; // TODO externalize

	public static Connection openConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_STRING);
	}
}
