package ph.kana.reor.model;

import java.math.BigDecimal;
import java.util.Optional;

public class Receipt extends Document {
	private BigDecimal amount;
	private Optional<Warranty> warranty;
	private Category category;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Optional<Warranty> getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = Optional.ofNullable(warranty);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
