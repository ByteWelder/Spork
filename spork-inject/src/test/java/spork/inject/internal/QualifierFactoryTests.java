package spork.inject.internal;

import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.reflect.Method;

import javax.inject.Named;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QualifierFactoryTests {

	@Qualifier
	@Retention(RUNTIME)
	@interface QualifierWithoutValueMethod {
	}

	@QualifierWithoutValueMethod
	private static void noQualifierMethod() {
	}

	@Named("something")
	private static void specifiedValueQualifierMethod() {
	}

	@Named
	private static void defaultValueQualifierMethod() {
	}

	@Test
	public void noValueMethodQualifier() throws NoSuchMethodException {
		QualifierFactory factory = new QualifierFactory();
		Method method = getClass().getDeclaredMethod("noQualifierMethod");
		QualifierWithoutValueMethod named = method.getAnnotation(QualifierWithoutValueMethod.class);
		String qualifier = factory.create(named);

		assertThat(qualifier, is("spork.inject.internal.QualifierFactoryTests$QualifierWithoutValueMethod"));
	}

	@Test
	public void specifiedValueQualifier() throws NoSuchMethodException {
		QualifierFactory factory = new QualifierFactory();
		Method method = getClass().getDeclaredMethod("specifiedValueQualifierMethod");
		Named named = method.getAnnotation(Named.class);
		String qualifier = factory.create(named);

		assertThat(qualifier, is("javax.inject.Named:something"));
	}

	@Test
	public void defaultValueQualifier() throws NoSuchMethodException {
		QualifierFactory factory = new QualifierFactory();
		Method method = getClass().getDeclaredMethod("defaultValueQualifierMethod");
		Named named = method.getAnnotation(Named.class);
		String qualifier = factory.create(named);

		assertThat(qualifier, is("javax.inject.Named:"));
	}
}
