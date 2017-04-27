package spork.matchers;

import org.hamcrest.Matcher;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.allOf;

public final class Matchers {

	private Matchers() {
	}

	public static Matcher<Annotation> annotationOfType(Class<? extends Annotation> annotationType) {
		return new AnnotationTypeMatcher(annotationType);
	}

	public static Matcher<Class<?>> classFinal() {
		return new ClassFinalMatcher();
	}

	public static Matcher<Class<?>> constructors(int expectedCount) {
		return new ConstructorCountMatcher(expectedCount);
	}

	public static Matcher<Class<?>> oneConstructor() {
		return constructors(1);
	}

	public static Matcher<Class<?>> constructor(AccessModifier accessModifier, Class<?>... expectedArguments) {
		return new ConstructorMatcher(accessModifier, expectedArguments);
	}

	public static Matcher<Class<?>> defaultConstructor(AccessModifier accessModifier) {
		return constructor(accessModifier);
	}

	public static Matcher<Class<?>> classMethodsStatic() {
		return new ClassMethodsStaticMatcher();
	}

	public static Matcher<Class<?>> utilityClass() {
		return allOf(
				classFinal(),
				oneConstructor(),
				defaultConstructor(AccessModifier.PRIVATE),
				classMethodsStatic());
	}
}
