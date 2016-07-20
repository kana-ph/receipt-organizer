package ph.kana.reor.exception;

public class ServiceException extends Exception {

	public ServiceException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
