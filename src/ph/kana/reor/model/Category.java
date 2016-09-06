package ph.kana.reor.model;

import java.util.Optional;

public class Category extends Model {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		Optional
			.ofNullable(value)
			.ifPresent(v -> this.value = v);
	}
}
