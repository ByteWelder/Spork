package spork.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class ConstructorCountMatcher extends TypeSafeMatcher<Class<?>> {
	private final int expectedCount;

	ConstructorCountMatcher(int expectedCount) {
		this.expectedCount = expectedCount;
	}

	@Override
	protected boolean matchesSafely(Class item) {
		return item.getDeclaredConstructors().length == expectedCount;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("class should have ")
				.appendValue(expectedCount)
				.appendText(" constructor(s)");
	}

	@Override
	protected void describeMismatchSafely(Class<?> item, Description mismatchDescription) {
		mismatchDescription.appendText("was ")
				.appendValue(item.getDeclaredConstructors().length);
	}
}
