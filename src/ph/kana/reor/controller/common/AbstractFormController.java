package ph.kana.reor.controller.common;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BooleanSupplier;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.BigDecimalStringConverter;

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

	protected final void submit(Runnable save) {
		if (validateForm()) {
			save.run();
			clearMessages();
		}
	}

	protected final TextFormatter<BigDecimal> getBigDecimalTextFormatter() {
		return new TextFormatter(new BigDecimalStringConverter(), BigDecimal.ZERO);
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
		warningValidationRules.stream()
			.forEach((rule) -> rule.run());
	}
}
