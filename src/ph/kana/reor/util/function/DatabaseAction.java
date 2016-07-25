package ph.kana.reor.util.function;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseAction {
	void run(Connection connection) throws SQLException;
}
