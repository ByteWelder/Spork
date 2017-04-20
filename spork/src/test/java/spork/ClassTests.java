package spork;

import org.junit.Test;

import spork.internal.Reflection;
import spork.internal.SporkExtensionLoader;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		ClassAsserts.assertUtilityClassWellDefined(Reflection.class);
		ClassAsserts.assertUtilityClassWellDefined(SporkExtensionLoader.class);
	}
}
