package ph.kana.reor.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

	public static String format(LocalDate date) {
		return date.format(DATE_FORMAT);
	}

	private DateUtil() {}

}
