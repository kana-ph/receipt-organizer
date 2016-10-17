package ph.kana.reor.type;

public enum EmptyListMessage {
	ERROR_LOADING("Error loading documents"),
	NOTHING_YET("There are no documents saved yet."),
	NO_SEARCH_RESULTS("Search query returned nothing.");

	private final String message;

	EmptyListMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
