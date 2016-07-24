package ph.kana.reor.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;


public interface ReceiptService {
	Receipt createReceipt(String title, BigDecimal amount, LocalDate receiptDate, Set<File> attachments, String description, Warranty warranty, String category)
		throws ServiceException;
}
