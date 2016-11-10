package ph.kana.reor.exception;

public class ServiceException extends RuntimeException {

	public ServiceException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
