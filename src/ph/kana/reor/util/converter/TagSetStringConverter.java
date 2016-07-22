package ph.kana.reor.util.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javafx.util.StringConverter;

public class TagSetStringConverter extends StringConverter<Set<String>> {

	private static final String DELIMITER = "; ";

	@Override
	public String toString(Set<String> tags) {
		if (tags == null || tags.isEmpty()) {
			return "";
		}
		return String.join(DELIMITER, tags)
			.toLowerCase();
	}

	@Override
	public Set<String> fromString(String string) {
		if (string == null) {
			return Collections.EMPTY_SET;
		}
		string = string
			.toLowerCase()
			.trim();

		if (string.isEmpty()) {
			return Collections.EMPTY_SET;
		}

		String[] tags = string.split(DELIMITER);
		return new HashSet(Arrays.asList(tags));
	}

}
