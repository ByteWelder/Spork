package spork.exceptions;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Contextual object that is used when constructing exceptions.
 */
public class BindContext {
	private final Class<? extends Annotation> annotationClass;
	@Nullable private final String source;
	@Nullable private final String target;
	private final List<String> suggestions;

	BindContext(
			Class<? extends Annotation> annotationClass,
			@Nullable String source,
			@Nullable String target,
			List<String> suggestions) {
		this.annotationClass = annotationClass;
		this.source = source;
		this.target = target;
		this.suggestions = suggestions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);

		if (!suggestions.isEmpty()) {
			for (String suggestion : suggestions) {
				builder.append("\n - suggestion: ").append(suggestion);
			}
		}

		builder.append("\n - annotation: ")
				.append(annotationClass.getName());

		if (source != null) {
			builder.append("\n - binding from: ").append(source);
		}

		if (target != null) {
			builder.append("\n - binding into: ").append(target);
		}

		return builder.toString();
	}
}
