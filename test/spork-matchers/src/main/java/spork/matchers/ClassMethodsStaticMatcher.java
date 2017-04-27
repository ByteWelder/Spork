package spork.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class ClassMethodsStaticMatcher extends TypeSafeMatcher<Class<?>> {

	@Override
	protected boolean matchesSafely(Class<?> item) {
		for (final Method method : item.getDeclaredMethods()) {
			if (!Modifier.isStatic(method.getModifiers())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("all methods in the specified class should be static");
	}
}
