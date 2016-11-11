package ph.kana.reor.controller.common;

import ph.kana.reor.model.Document;

public interface DocumentStatefulController<T extends Document> {
	void accept(T document);
}
