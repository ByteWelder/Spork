package spork.inject;

import org.junit.Test;

import spork.inject.internal.lang.Annotations;
import spork.inject.internal.ObjectGraphs;
import spork.internal.Reflection;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		ClassAsserts.assertUtilityClassWellDefined(Reflection.class);
		ClassAsserts.assertUtilityClassWellDefined(AnnotationSerializers.class);
		ClassAsserts.assertUtilityClassWellDefined(ObjectGraphs.class);
		ClassAsserts.assertUtilityClassWellDefined(Annotations.class);
	}
}
