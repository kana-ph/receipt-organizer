package ph.kana.reor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public final class ConnectionManager {

	private ConnectionManager() {}

	private static final String CONNECTION_STRING = "jdbc:derby:db/organizer;create=true"; // TODO externalize

	public static Connection openConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_STRING);
	}

	public static void prepareDatabase() {
		try (Connection connection = openConnection()) {
			List<String> tables = Arrays
				.asList("attachment", "category", "document", "receipt", "warranty");
			boolean dbPrepared = tables.parallelStream()
				.map(table -> testTable(connection, table))
				.reduce(true, Boolean::logicalAnd);

			if (!dbPrepared) {
				initializeTables(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	private static boolean testTable(Connection connection, String table) {
		String sql = String.format("SELECT id FROM %s FETCH FIRST 1 ROWS ONLY", table);
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(sql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static void initializeTables(Connection connection) throws SQLException {
		InputStream stream = ConnectionManager.class
			.getResourceAsStream("/ph/kana/reor/dao/derby/schema/schema.sql");
		StringBuffer fileContents = new StringBuffer();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			reader.lines()
				.forEachOrdered(fileContents::append);
			System.out.println(fileContents);

			Statement statement = connection.createStatement();
			statement.executeUpdate(fileContents.toString());

		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
