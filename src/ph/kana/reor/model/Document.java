package ph.kana.reor.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class Document {
	private String title;
	private LocalDate date;
	private String description;

	private Set tags;
	private Set attachments;

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

	public Set getTags() {
		return (tags == null)? (new HashSet()) : tags;
	}

	public void setTags(Set tags) {
		this.tags = tags;
	}

	public Set getAttachments() {
		return (attachments == null)? (new HashSet()) : attachments;
	}

	public void setAttachments(Set attachments) {
		this.attachments = attachments;
	}
}
