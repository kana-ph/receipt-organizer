package ph.kana.reor.model;

import java.math.BigDecimal;

public class Receipt extends Document {
	private BigDecimal amount;
	//private Warranty warranty;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

//	public Warranty getWarranty() {
//		return warranty;
//	}
//
//	public void setWarranty(Warranty warranty) {
//		this.warranty = warranty;
//	}
}
