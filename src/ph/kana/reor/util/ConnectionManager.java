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

	private static final String CONNECTION_STRING = "jdbc:derby:db/reor;create=true";

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

	private static void initializeTables(Connection connection) {
		InputStream stream = ConnectionManager.class
			.getResourceAsStream("/ph/kana/reor/dao/derby/schema/schema.sql");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			StringBuilder fileContents = new StringBuilder();
			reader.lines()
				.forEachOrdered(fileContents::append);

			List<String> sqlStatements = Arrays.asList(fileContents.toString().split(";"));
			sqlStatements.stream()
				.forEachOrdered(sql -> executeSql(connection, sql));
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	private static void executeSql(Connection connection, String sql) {
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
