package spork.inject;

import org.junit.Test;

import spork.inject.internal.lang.Annotations;
import spork.inject.internal.ObjectGraphs;
import spork.internal.Reflection;

import static org.junit.Assert.assertThat;
import static spork.matchers.Matchers.utilityClass;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		assertThat(Reflection.class, utilityClass());
		assertThat(ObjectGraphs.class, utilityClass());
		assertThat(Annotations.class, utilityClass());
	}
}
