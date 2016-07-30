package ph.kana.reor.exception;

public class DataAccessException extends Exception {

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
