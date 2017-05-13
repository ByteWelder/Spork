package spork.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings({ "PMD.AvoidStringBufferField", "PMD.TooManyMethods" })
public class ExceptionMessageBuilder {
	private final StringBuilder builder = new StringBuilder(200);

	public ExceptionMessageBuilder(String message) {
		builder.append(message);
	}

	public final ExceptionMessageBuilder annotation(Class<? extends Annotation> annotationClass) {
		entryBuilder().append("annotation: ").append(annotationClass.getName());
		return this;
	}

	/**
	 * Defines the source of the Exception (e.g. the data that was bound to a target object)
	 */
	public final ExceptionMessageBuilder bindingFrom(Class<?> type) {
		return bindingFrom(type.getName());
	}

	/**
	 * Defines the source of the Exception (e.g. the Method that provides data to be bound to a target object)
	 */
	public final ExceptionMessageBuilder bindingFrom(Method method) {
		return bindingFrom(method.toString());
	}

	/**
	 * Defines the target type to inject to.
	 */
	public final ExceptionMessageBuilder bindingInto(Class<?> type) {
		return bindingInto(type.getName());
	}

	/**
	 * Defines the target Field to inject to.
	 */
	public final ExceptionMessageBuilder bindingInto(Field field) {
		return bindingInto(field.toString());
	}

	public final ExceptionMessageBuilder bindingFrom(String from) {
		entryBuilder().append("binding from: ").append(from);
		return this;
	}

	/**
	 * Defines the description of the injection target.
	 */
	public final ExceptionMessageBuilder bindingInto(String description) {
		entryBuilder().append("binding into: ").append(description);
		return this;
	}

	/**
	 * Defines the target Method to inject to.
	 */
	public final ExceptionMessageBuilder bindingInto(Method method) {
		return bindingInto(method.toString());
	}

	/**
	 * Add a suggestion that could solve the problem.
	 */
	public final ExceptionMessageBuilder suggest(String suggestion) {
		entryBuilder().append("suggestion: ").append(suggestion);
		return this;
	}

	// region Finalization methods

	public final String build() {
		return builder.toString();
	}

	// internal methods

	private StringBuilder entryBuilder() {
		return builder.append("\n - ");
	}
}
