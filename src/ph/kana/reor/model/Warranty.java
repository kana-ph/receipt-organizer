package ph.kana.reor.model;

import java.time.LocalDate;

public class Warranty extends Model {
	private LocalDate expiration;

	private Document document;

	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public boolean isLifetime() {
		return (expiration == null);
	}
}
