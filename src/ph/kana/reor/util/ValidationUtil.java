package ph.kana.reor.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;

public class ValidationUtil {

	public static boolean validateRequiredValue(Object value) {
		if (value == null) {
			return false;
		}
		return !((value instanceof String) && ((String) value).isEmpty());
	}

	public static boolean validatePositiveNumber(BigDecimal value, boolean zeroAllowed) {
		if (zeroAllowed && (value.compareTo(BigDecimal.ZERO) < 0)) {
			return false;
		} else if (!zeroAllowed && (value.compareTo(BigDecimal.ZERO) <= 0)) {
			return false;
		}
		return true;
	}

	public static boolean validateHasAttachments(Collection<File> attachments) {
		return  !attachments.isEmpty();
	}
}
