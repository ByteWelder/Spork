package spork.internal;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import spork.exceptions.ExceptionMessageBuilder;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExceptionMessageBuilderTests {
	private int reflectionField = 0;

	@Test
	public void defaultConstructor() {
		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.build();

		assertThat(message, is("\n - annotation: org.junit.Rule"));
	}

	@Test
	public void bindingFromClassIntoClass() {
		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.bindingFrom(String.class)
				.bindingInto(Field.class)
				.build();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding from: java.lang.String"
				+ "\n - binding into: java.lang.reflect.Field"));
	}

	@Test
	public void bindingFromMethodIntoMethod() throws NoSuchMethodException {
		Method noArgumentsMethod = getClass().getDeclaredMethod("testMethodNoArguments");
		Method oneArgumentMethod = getClass().getDeclaredMethod("testMethodOneArgument", int.class);

		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.bindingFrom(noArgumentsMethod)
				.bindingInto(oneArgumentMethod)
				.build();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding from: spork.internal.ExceptionMessageBuilderTests.testMethodNoArguments()"
				+ "\n - binding into: spork.internal.ExceptionMessageBuilderTests.testMethodOneArgument(...)"));
	}

	@Test
	public void bindingIntoField() throws NoSuchFieldException {
		Field field = getClass().getDeclaredField("reflectionField");

		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.bindingInto(field)
				.build();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding into: spork.internal.ExceptionMessageBuilderTests.reflectionField"));
	}

	@Test
	public void bindingIntoString() throws NoSuchFieldException {
		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.bindingInto("something")
				.build();

		assertThat(message, is("\n - annotation: org.junit.Rule"
				+ "\n - binding into: something"));
	}

	@Test
	public void suggestOne() {
		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.suggest("suggested")
				.build();

		assertThat(message, containsString("\n - suggestion: suggested"));
	}

	@Test
	public void suggestMultiple() {
		String message = new ExceptionMessageBuilder("")
				.annotation(Rule.class)
				.suggest("suggested1")
				.suggest("suggested2")
				.build();

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
