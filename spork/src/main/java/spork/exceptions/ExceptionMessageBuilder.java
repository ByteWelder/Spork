package spork.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("PMD.AvoidStringBufferField")
public class ExceptionMessageBuilder {
	private final StringBuilder builder = new StringBuilder(200);

	public ExceptionMessageBuilder(String message) {
		builder.append(message);
	}

	public ExceptionMessageBuilder annotation(Class<? extends Annotation> annotationClass) {
		builder.append("\n - annotation: ").append(annotationClass.getName());
		return this;
	}

	/**
	 * Defines the source of the Exception (e.g. the data that was bound to a target object)
	 */
	public ExceptionMessageBuilder bindingFrom(Class<?> type) {
		return bindingFrom(type.getName());
	}

	/**
	 * Defines the source of the Exception (e.g. the Method that provides data to be bound to a target object)
	 */
	public ExceptionMessageBuilder bindingFrom(Method method) {
		return bindingFrom(method.toString());
	}

	/**
	 * Defines the target type to inject to.
	 */
	public ExceptionMessageBuilder bindingInto(Class<?> type) {
		return bindingInto(type.getName());
	}

	/**
	 * Defines the target Field to inject to.
	 */
	public ExceptionMessageBuilder bindingInto(Field field) {
		return bindingInto(field.toString());
	}

	private ExceptionMessageBuilder bindingFrom(String from) {
		builder.append("\n - binding from: ").append(from);
		return this;
	}

	/**
	 * Defines the description of the injection target.
	 */
	public ExceptionMessageBuilder bindingInto(String description) {
		builder.append("\n - binding into: ").append(description);
		return this;
	}

	/**
	 * Defines the target Method to inject to.
	 */
	public ExceptionMessageBuilder bindingInto(Method method) {
		return bindingInto(method.toString());
	}

	/**
	 * Add a suggestion that could solve the problem.
	 */
	public ExceptionMessageBuilder suggest(String suggestion) {
		builder.append("\n - suggestion: ").append(suggestion);
		return this;
	}

	// region Finalization methods

	public String build() {
		return builder.toString();
	}
}
