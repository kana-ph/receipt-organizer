package ph.kana.reor.validator;

import java.util.function.Predicate;

public class ValidationRule {
	private final FieldInfo fieldInfo;
	private final Predicate<FieldInfo> validation;

	public ValidationRule(FieldInfo fieldInfo, Predicate<FieldInfo> validation) {
		this.fieldInfo = fieldInfo;
		this.validation = validation;
	}

	public boolean test() {
		return validation.test(fieldInfo);
	}
}
