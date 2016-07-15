package ph.kana.reor.model;

import java.time.LocalDate;

public class Warranty {
	private Long id;
	private LocalDate expiration;

	private Document document;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
