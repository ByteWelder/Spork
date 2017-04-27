package spork.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Modifier;

class ClassFinalMatcher extends TypeSafeMatcher<Class<?>> {

	@Override
	protected boolean matchesSafely(Class actualClass) {
		return Modifier.isFinal(actualClass.getModifiers());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("class should be final");
	}
}
