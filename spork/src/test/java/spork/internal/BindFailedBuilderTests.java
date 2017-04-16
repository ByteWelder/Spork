package spork.internal;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import spork.BindFailed;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static spork.internal.BindFailedBuilder.bindFailedBuilder;

public class BindFailedBuilderTests {

	@Test
	public void defaultConstructor() {
		String message = bindFailedBuilder(Rule.class, "reason")
				.build()
				.getMessage();

		assertThat(message, is("Failed to bind annotation Rule: reason\n"
				+ "\tannotation: org.junit.Rule"));
	}

	@Test
	public void fromInto() {
		String message = bindFailedBuilder(Rule.class, "reason")
				.from(String.class)
				.into(Field.class)
				.build()
				.getMessage();

		assertThat(message, is("Failed to bind annotation Rule: reason\n"
				+ "\tannotation: org.junit.Rule\n"
				+ "\tbinding from: java.lang.String\n"
				+ "\tbinding into: java.lang.reflect.Field"));
	}

	@Test
	public void cause() {
		Throwable cause = new Exception("cause");
		BindFailed bindFailed = bindFailedBuilder(Rule.class, "reason")
				.cause(cause)
				.build();

		assertThat(bindFailed.getCause(), is(cause));
	}
}
