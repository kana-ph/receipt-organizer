package ph.kana.reor.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import ph.kana.reor.dao.AttachmentDao;
import ph.kana.reor.dao.ReceiptDao;
import ph.kana.reor.dao.WarrantyDao;
import ph.kana.reor.dao.derby.DerbyAttachmentDao;
import ph.kana.reor.dao.derby.DerbyReceiptDao;
import ph.kana.reor.dao.derby.DerbyWarrantyDao;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.exception.ServiceException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;
import ph.kana.reor.model.Receipt;
import ph.kana.reor.model.Warranty;


public class ReceiptService {
	private final AttachmentService attachmentService = new AttachmentService();
	private final CategoryService categoryService = new CategoryService();

	private final AttachmentDao attachmentDao = new DerbyAttachmentDao();
	private final ReceiptDao receiptDao = new DerbyReceiptDao();
	private final WarrantyDao warrantyDao = new DerbyWarrantyDao();

	public Receipt createReceipt(String title, BigDecimal amount, LocalDate receiptDate, Set<File> attachments, String description, Warranty warranty, String category)
		throws ServiceException {
		try {
			Receipt receipt = new Receipt();
			receipt.setTitle(title);
			receipt.setAmount(amount);
			receipt.setDate(receiptDate);
			receipt.setDescription(description);
			receipt.setWarranty(warranty);
			receipt.setCategory(categoryService.fetchCategory(category));

			receipt = receiptDao.save(receipt);
			saveWarranty(receipt);
			saveAttachments(receipt, attachments);

			return receipt;
		} catch (DataAccessException | IOException e) {
			throw new ServiceException(e);
		}
	}

	private Set<Attachment> transformFilesToAttachments(Document document, Set<File> files) {
		Set<Attachment> attachments = new HashSet();
		files.stream()
			.map(attachmentService::buildInstance)
			.peek(a -> a.setDocument(document))
			.forEach(attachments::add);

		return attachments;
	}

	private void saveWarranty(Receipt receipt) throws DataAccessException {
		Warranty warranty = receipt
			.getWarranty()
			.orElse(null);

		if (warranty != null) {
			warranty.setDocument(receipt);
			warrantyDao.save(warranty);
		}
	}

	private void saveAttachments(Receipt receipt, Set<File> attachmentFiles) throws DataAccessException, IOException {
		Set<Attachment> attachments = transformFilesToAttachments(receipt, attachmentFiles);

		for (Attachment attachment : attachments) {
			attachmentDao.save(attachment);
		}
	}
}
