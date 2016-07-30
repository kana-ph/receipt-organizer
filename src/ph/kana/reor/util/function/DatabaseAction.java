package ph.kana.reor.util.function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseAction {
	ResultSet run(Connection connection) throws SQLException;
}
