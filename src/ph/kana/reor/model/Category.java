package ph.kana.reor.model;

public class Category extends Model {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value != null) {
			this.value = value.toUpperCase();
		}
	}
}
