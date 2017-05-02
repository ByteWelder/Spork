package spork.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class BindContextBuilder {
	private final Class<? extends Annotation> annotationClass;
	@Nullable private String source;
	@Nullable private String target;
	private final List<String> suggestions = new ArrayList<>();

	public BindContextBuilder(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	/**
	 * Defines the source of the Exception (e.g. the data that was bound to a target object)
	 */
	public BindContextBuilder bindingFrom(Class<?> type) {
		this.source = type.getName();
		return this;
	}

	/**
	 * Defines the source of the Exception (e.g. the Method that provides data to be bound to a target object)
	 */
	public BindContextBuilder bindingFrom(Method method) {
		this.source = getMethodSignature(method);
		return this;
	}

	/**
	 * Defines the target type to inject to.
	 */
	public BindContextBuilder bindingInto(Class<?> type) {
		this.target = type.getName();
		return this;
	}

	/**
	 * Defines the target Field to inject to.
	 */
	public BindContextBuilder bindingInto(Field field) {
		this.target = getFieldSignature(field);
		return this;
	}

	/**
	 * Defines the description of the injection target.
	 */
	public BindContextBuilder bindingInto(String description) {
		this.target = description;
		return this;
	}

	/**
	 * Defines the target Method to inject to.
	 */
	public BindContextBuilder bindingInto(Method method) {
		this.target = getMethodSignature(method);
		return this;
	}

	/**
	 * Add a suggestion that could solve the problem.
	 */
	public BindContextBuilder suggest(String suggestion) {
		this.suggestions.add(suggestion);
		return this;
	}

	// region Finalization methods

	public BindContext build() {
		return new BindContext(annotationClass, source, target, suggestions);
	}

	// region Reflection signature methods

	private static String getMethodSignature(Method method) {
		String parameters = method.getParameterTypes().length == 0 ? "" : "...";
		return method.getDeclaringClass().getName() + "." + method.getName() + "(" + parameters + ")";
	}

	private static String getFieldSignature(Field field) {
		return field.getDeclaringClass().getName() + "." + field.getName();
	}

	// endregion
}
