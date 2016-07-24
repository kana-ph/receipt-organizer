package ph.kana.reor.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class Document {
	private Long id;
	private String title;
	private LocalDate date;
	private String description;

	private Set<Attachment> attachments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getAttachments() {
		return (attachments == null)? (new HashSet()) : attachments;
	}

	public void setAttachments(Set attachments) {
		this.attachments = attachments;
	}
}
