package ph.kana.reor.type;

public enum DashboardClass {

	DOCUMENT_CARD("document-card");

	private final String className;

	private DashboardClass(String className) {
		this.className = className;
	}

	public String getName() {
		return className;
	}

}
