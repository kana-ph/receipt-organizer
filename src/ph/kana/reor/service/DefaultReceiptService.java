package ph.kana.reor.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Warranty;

public class DefaultReceiptService implements ReceiptService {

	@Override
	public void createReceipt(String title, BigDecimal amount, LocalDate receiptDate, List<File> attachments, String description, Warranty warranty, String category) throws ServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
