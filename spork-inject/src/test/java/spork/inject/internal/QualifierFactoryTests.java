package spork.inject.internal;

import org.junit.Test;

import java.lang.reflect.Method;

import javax.inject.Named;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QualifierFactoryTests {

	@Test
	@Named("something")
	public void test() throws NoSuchMethodException {
		QualifierFactory factory = new QualifierFactory();
		Method method = getClass().getMethod("test");
		Named named = method.getAnnotation(Named.class);
		String qualifier = factory.create(named);

		assertThat(qualifier, is("javax.inject.Named:something"));
	}
}
