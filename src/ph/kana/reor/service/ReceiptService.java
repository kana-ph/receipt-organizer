package ph.kana.reor.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Warranty;


public interface ReceiptService {
	void createReceipt(String title, BigDecimal amount, LocalDate receiptDate, List<File> attachments, String description, Warranty warranty, String category)
		throws ServiceException;
}
