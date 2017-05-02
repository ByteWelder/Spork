package spork.internal;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import spork.exceptions.BindContextBuilder;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BindContextBuilderTests {
	private int reflectionField = 0;

	@Test
	public void defaultConstructor() {
		String message = new BindContextBuilder(Rule.class)
				.build()
				.toString();

		assertThat(message, is("\n - annotation: org.junit.Rule"));
	}

	@Test
	public void fromClassIntoClass() {
		String message = new BindContextBuilder(Rule.class)
				.bindingFrom(String.class)
				.bindingInto(Field.class)
				.build()
				.toString();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding from: java.lang.String"
				+ "\n - binding into: java.lang.reflect.Field"));
	}

	@Test
	public void fromMethodIntoMethod() throws NoSuchMethodException {
		Method noArgumentsMethod = getClass().getDeclaredMethod("testMethodNoArguments");
		Method oneArgumentMethod = getClass().getDeclaredMethod("testMethodOneArgument", int.class);

		String message = new BindContextBuilder(Rule.class)
				.bindingFrom(noArgumentsMethod)
				.bindingInto(oneArgumentMethod)
				.build()
				.toString();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding from: spork.internal.BindContextBuilderTests.testMethodNoArguments()"
				+ "\n - binding into: spork.internal.BindContextBuilderTests.testMethodOneArgument(...)"));
	}

	@Test
	public void intoField() throws NoSuchFieldException {
		Field field = getClass().getDeclaredField("reflectionField");

		String message = new BindContextBuilder(Rule.class)
				.bindingInto(field)
				.build()
				.toString();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding into: spork.internal.BindContextBuilderTests.reflectionField"));
	}

	@Test
	public void suggestOne() {
		String message = new BindContextBuilder(Rule.class)
				.suggest("suggested")
				.build()
				.toString();

		assertThat(message, containsString("\n - suggestion: suggested"));
	}

	@Test
	public void suggestMultiple() {
		String message = new BindContextBuilder(Rule.class)
				.suggest("suggested1")
				.suggest("suggested2")
				.build()
				.toString();

		assertThat(message, allOf(
				containsString("\n - suggestion: suggested1"),
				containsString("\n - suggestion: suggested2")
		));
	}

	private void testMethodNoArguments() {
	}

	private void testMethodOneArgument(int value) {
	}
}
