package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import spork.BindFailed;

/**
 * Builder pattern for {@link BindFailed}
 */
@SuppressWarnings("PMD.TooManyMethods")
final public class BindFailedBuilder {
	private final String message;
	private final Class<? extends Annotation> annotationClass;
	@Nullable private Throwable cause;
	@Nullable private String source;
	@Nullable private String target;
	private final List<String> suggestions = new ArrayList<>();

	private BindFailedBuilder(Class<? extends Annotation> annotationClass, String message) {
		this.annotationClass = annotationClass;
		this.message = message;
	}

	public BindFailedBuilder cause(Throwable cause) {
		this.cause = cause;
		return this;
	}

	/**
	 * Defines the source of the Exception (e.g. the data that was bound to a target object)
	 */
	public BindFailedBuilder from(Class<?> type) {
		this.source = type.getName();
		return this;
	}

	/**
	 * Defines the source of the Exception (e.g. the Method that provides data to be bound to a target object)
	 */
	public BindFailedBuilder from(Method method) {
		this.source = getMethodSignature(method);
		return this;
	}

	/**
	 * Defines the target type to inject to.
	 */
	public BindFailedBuilder into(Class<?> type) {
		this.target = type.getName();
		return this;
	}

	/**
	 * Defines the target Field to inject to.
	 */
	public BindFailedBuilder into(Field field) {
		this.target = getFieldSignature(field);
		return this;
	}

	/**
	 * Defines the description of the injection target.
	 */
	public BindFailedBuilder into(String description) {
		this.target = description;
		return this;
	}

	/**
	 * Defines the target Method to inject to.
	 */
	public BindFailedBuilder into(Method method) {
		this.target = getMethodSignature(method);
		return this;
	}

	/**
	 * Add a suggestion that could solve the problem.
	 */
	public BindFailedBuilder suggest(String suggestion) {
		this.suggestions.add(suggestion);
		return this;
	}

	// region Finalization methods

	public BindFailed build() {
		String compoundMessage = buildMessage();
		if (cause == null) {
			return new BindFailed(compoundMessage);
		} else {
			return new BindFailed(compoundMessage, cause);
		}
	}

	private String buildMessage() {
		StringBuilder builder = new StringBuilder(200)
				.append("Failed to bind annotation ")
				.append(annotationClass.getSimpleName())
				.append(": ")
				.append(message);

		if (!suggestions.isEmpty()) {
			for (String suggestion : suggestions) {
				builder.append("\n\t- suggestion: ").append(suggestion);
			}
		}

		builder.append("\n\t- annotation: ")
				.append(annotationClass.getName());

		if (source != null) {
			builder.append("\n\t- binding from: ").append(source);
		}

		if (target != null) {
			builder.append("\n\t- binding into: ").append(target);
		}

		return builder.toString();
	}

	// endregion

	// region Static constructor

	public static BindFailedBuilder bindFailedBuilder(Class<? extends Annotation> annotationClass, String message) {
		return new BindFailedBuilder(annotationClass, message);
	}

	// endregion

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
