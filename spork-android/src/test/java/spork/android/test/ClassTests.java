package spork.android.test;

import org.junit.Test;

import spork.internal.SporkExtensionLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static spork.matchers.Matchers.utilityClass;

public class ClassTests {

	@Test
	public void utilityClasses() {
		assertThat(SporkExtensionLoader.class, utilityClass());
	}
}
