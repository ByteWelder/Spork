package spork.exceptions;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.Nullable;

public class BindContext {
	@Nullable private final Class<? extends Annotation> annotationClass;
	@Nullable private final String source;
	@Nullable private final String target;
	private final List<String> suggestions;

	BindContext(
			@Nullable Class<? extends Annotation> annotationClass,
			@Nullable String source,
			@Nullable String target,
			List<String> suggestions) {
		this.annotationClass = annotationClass;
		this.source = source;
		this.target = target;
		this.suggestions = suggestions;
	}

	@Nullable
	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
	}

	@Nullable
	public String getSource() {
		return source;
	}

	@Nullable
	public String getTarget() {
		return target;
	}

	public List<String> getSuggestions() {
		return suggestions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);

		if (!suggestions.isEmpty()) {
			for (String suggestion : suggestions) {
				builder.append("\n - suggestion: ").append(suggestion);
			}
		}

		if (annotationClass != null) {
			builder.append("\n - annotation: ")
					.append(annotationClass.getName());
		}

		if (source != null) {
			builder.append("\n - binding from: ").append(source);
		}

		if (target != null) {
			builder.append("\n - binding into: ").append(target);
		}

		return builder.toString();
	}
}
