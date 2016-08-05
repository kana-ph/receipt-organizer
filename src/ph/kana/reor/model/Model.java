package ph.kana.reor.model;

public abstract class Model {
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
