package ph.kana.reor.util;

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
}
