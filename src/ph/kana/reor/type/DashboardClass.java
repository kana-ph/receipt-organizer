package ph.kana.reor.type;

public enum DashboardClass {

	AMOUNT,
	CATEGORY,
	DESCRIPTION_BOX,
	DOCUMENT_CARD,
	FIELD_NAME,
	OPTIONS_LINK,
	TITLE,
	WARRANTY_ACTIVE,
	WARRANTY_INACTIVE;

	private final String className;

	private DashboardClass() {
		className = name()
			.toLowerCase()
			.replace('_', '-');
	}

	public String getName() {
		return className;
	}

}
