package ph.kana.reor.util.function;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<E> {
	E map(ResultSet resultSet) throws SQLException;
}
