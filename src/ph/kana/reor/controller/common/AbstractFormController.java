package ph.kana.reor.controller.common;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.BigDecimalStringConverter;
import ph.kana.reor.validator.ValidationRule;

public abstract class AbstractFormController extends AbstractWindowController implements Initializable {

	private List<ValidationRule> errorValidationRules;
	private List<ValidationRule> warningValidationRules;

	protected abstract List<ValidationRule> addErrorValidations();
	protected abstract List<ValidationRule> addWarningValidations();
	protected abstract void initializeForm();

	@Override
	public final void initialize(URL url, ResourceBundle resourceBundle) {
		initializeForm();
		errorValidationRules = addErrorValidations();
		warningValidationRules = addWarningValidations();
	}

	protected final void submit(Runnable save) {
		if (validateForm()) {
			save.run();
		}
	}

	protected final TextFormatter<BigDecimal> getBigDecimalTextFormatter() {
		return new TextFormatter(new BigDecimalStringConverter(), BigDecimal.ZERO);
	}

	private final boolean validateForm() {
		boolean formValid = testValidityByRules(errorValidationRules);
		testValidityByRules(warningValidationRules);

		return formValid;
	}

	protected boolean testValidityByRules(List<ValidationRule> rules) {
		boolean valid = true;
		return rules.stream()
			.map((ValidationRule rule) -> rule.test())
			.reduce(valid, Boolean::logicalAnd);
	}
}
