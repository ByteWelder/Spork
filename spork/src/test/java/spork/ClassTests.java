package spork;

import org.junit.Test;

import spork.internal.Reflection;
import spork.internal.SporkExtensionLoader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static spork.matchers.Matchers.utilityClass;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		assertThat(Spork.class, is(utilityClass()));
		assertThat(Reflection.class, is(utilityClass()));
		assertThat(SporkExtensionLoader.class, is(utilityClass()));
	}
}
