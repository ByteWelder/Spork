package spork.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Constructor;

import static spork.matchers.AccessModifier.accessModifierOf;

class ConstructorMatcher extends TypeSafeMatcher<Class<?>> {
	private final AccessModifier expectedAccessModifier;
	private final Class<?>[] expectedArgumentTypes;

	ConstructorMatcher(AccessModifier expectedAccessModifier, Class<?>... expectedArgumentTypes) {
		this.expectedAccessModifier = expectedAccessModifier;
		this.expectedArgumentTypes = expectedArgumentTypes;
	}

	@Override
	protected boolean matchesSafely(Class<?> item) {
		try {
			// find constructor (or fail by Exception)
			Constructor constructor = item.getDeclaredConstructor(expectedArgumentTypes);

			// invoke default constructor to improve test coverage
			if (expectedArgumentTypes.length == 0) {
				try {
					constructor.newInstance();
				} catch (Exception e) {
					// ignore
				}
			}

			// Check access modifier
			AccessModifier accessModifier = accessModifierOf(constructor);
			if (accessModifier != expectedAccessModifier) {
				return false;
			}

			// else, all is good
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("class constructor should have ");
		description.appendText(expectedAccessModifier.toString().toLowerCase());
		description.appendText(" access modifier with constructor arguments (");

		int maxIndex = expectedArgumentTypes.length;
		for (int i = 0; i < expectedArgumentTypes.length; ++i) {
			description.appendText(expectedArgumentTypes[i].getSimpleName());

			if (i < maxIndex) {
				description.appendText(", ");
			}
		}

		description.appendText(")");
	}
}
