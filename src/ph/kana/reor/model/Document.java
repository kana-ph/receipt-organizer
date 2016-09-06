package ph.kana.reor.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class Document extends Model {
	private String title;
	private LocalDate date;
	private String description;

	private Set<Attachment> attachments;

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
		return Optional
			.ofNullable(attachments)
			.orElse(new HashSet());
	}

	public void setAttachments(Set attachments) {
		this.attachments = attachments;
	}
}
