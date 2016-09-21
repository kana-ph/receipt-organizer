package ph.kana.reor.type;

public enum EmptyListMessage {
	NOTHING_YET("There are no documents saved yet."),
	NO_SEARCH_RESULTS("Search query returned nothing.");

	private String message;
	EmptyListMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
