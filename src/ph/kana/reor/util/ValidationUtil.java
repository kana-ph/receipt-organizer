package ph.kana.reor.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import ph.kana.reor.exception.EmptyAttachmentException;
import ph.kana.reor.exception.ValidationException;

public class ValidationUtil {

	public static void validateRequiredValue(String field, Object value) throws ValidationException {
		if (value == null) {
			throw new ValidationException(field + " is required.");
		}
		if ((value instanceof String) && ((String) value).isEmpty()) {
			throw new ValidationException(field + " is required.");
		}
	}

	public static void validatePositiveNumber(String field, BigDecimal value, boolean zeroAllowed) throws ValidationException {
		if (zeroAllowed && (value.compareTo(BigDecimal.ZERO) < 0)) {
			throw new ValidationException(field + " cannot be lower than 0.");
		} else if (!zeroAllowed && (value.compareTo(BigDecimal.ZERO) <= 0)) {
			throw new ValidationException(field + " must be greater than 0.");
		}
	}

	public static void validateHasAttachments(Collection<File> attachments) throws EmptyAttachmentException {
		if (attachments.isEmpty()) {
			throw new EmptyAttachmentException();
		}
	}
}
