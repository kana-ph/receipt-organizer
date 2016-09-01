package ph.kana.reor.dao;

import java.util.Set;
import ph.kana.reor.exception.DataAccessException;
import ph.kana.reor.model.Attachment;
import ph.kana.reor.model.Document;

public abstract class AttachmentDao extends AbstractDao<Attachment> {

	public abstract Set<Attachment> findAllByDocument(Document document) throws DataAccessException;

	public abstract Attachment save(Attachment attachment) throws DataAccessException;
}
