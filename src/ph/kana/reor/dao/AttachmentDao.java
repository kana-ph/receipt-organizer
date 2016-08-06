package ph.kana.reor.dao;

import java.util.Set;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;

public interface AttachmentDao {
	Set<Attachment> findAllByDocument(Document document) throws DataAccessException;
}
