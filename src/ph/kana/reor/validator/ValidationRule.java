package ph.kana.reor.validator;

import java.util.function.Predicate;
import javafx.scene.control.Control;

public class ValidationRule<T extends Control> {
	private final T field;
	private final Predicate<T> validation;

	public ValidationRule(T value, Predicate<T> validation) {
		this.field = value;
		this.validation = validation;
	}

	public boolean test() {
		return validation.test(field);
	}
}
