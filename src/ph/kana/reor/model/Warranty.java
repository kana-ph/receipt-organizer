package ph.kana.reor.model;

import java.time.LocalDate;
import java.util.Optional;

public class Warranty extends Model {
	private Optional<LocalDate> expiration;

	private Document document;

	public Optional<LocalDate> getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = Optional.ofNullable(expiration);
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
