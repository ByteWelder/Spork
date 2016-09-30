package io.github.sporklibrary.test;

import org.junit.Test;

import io.github.sporklibrary.internal.Reflection;

import static io.github.sporklibrary.test.ClassAsserts.assertUtilityClassWellDefined;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		assertUtilityClassWellDefined(Reflection.class);
	}
}
