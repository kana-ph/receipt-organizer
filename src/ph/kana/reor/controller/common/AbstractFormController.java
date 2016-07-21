package ph.kana.reor.controller.common;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.BigDecimalStringConverter;
import ph.kana.reor.util.converter.TagSetStringConverter;
import ph.kana.reor.util.function.ThrowingRunnable;

public abstract class AbstractFormController extends AbstractWindowController implements Initializable {

	private List<BooleanSupplier> errorValidationRules;
	private List<Runnable> warningValidationRules;

	protected abstract List<BooleanSupplier> addErrorValidations();
	protected abstract List<Runnable> addWarningValidations();
	protected abstract void initializeForm();
	protected abstract void clearMessages();

	@Override
	public final void initialize(URL url, ResourceBundle resourceBundle) {
		initializeForm();
		errorValidationRules = addErrorValidations();
		warningValidationRules = addWarningValidations();
	}

	protected <T extends Throwable> void submit(ThrowingRunnable<T> submit, Consumer<T> handleException) {
		try {
			submit.runWithThrowable();
			clearMessages();
		} catch(Throwable t) {
			handleException.accept((T) t);
		}
	}

	protected final TextFormatter<BigDecimal> fetchBigDecimalTextFormatter() {
		return new TextFormatter(new BigDecimalStringConverter(), BigDecimal.ZERO);
	}

	protected final TextFormatter<Set<String>> fetchTagsTexFormatter() {
		return new TextFormatter(new TagSetStringConverter(), new HashSet());
	}

	private final boolean validateForm() {
		boolean formValid = testFormValidity();
		showWarnings();

		return formValid;
	}

	protected boolean testFormValidity() {
		boolean valid = true;
		return errorValidationRules.stream()
			.map((rule) -> rule.getAsBoolean())
			.reduce(valid, Boolean::logicalAnd);
	}

	private void showWarnings() {
		warningValidationRules
			.forEach((rule) -> rule.run());
	}
}
