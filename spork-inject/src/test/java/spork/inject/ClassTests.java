package spork.inject;

import org.junit.Test;

import spork.internal.Reflection;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		ClassAsserts.assertUtilityClassWellDefined(Reflection.class);
	}
}
