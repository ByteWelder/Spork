package spork.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.lang.annotation.Annotation;

class AnnotationTypeMatcher extends TypeSafeDiagnosingMatcher<Annotation> {
	private final Class<? extends Annotation> expectedType;

	AnnotationTypeMatcher(Class<? extends Annotation> expectedType) {
		this.expectedType = expectedType;
	}

	@Override
	protected boolean matchesSafely(Annotation actualAnnotation, Description mismatchDescription) {
		return actualAnnotation.annotationType().equals(expectedType);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(expectedType.getName());
	}
}
