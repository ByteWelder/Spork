package io.github.sporklibrary.test;

import org.junit.Test;

import io.github.sporklibrary.internal.AnnotatedFields;
import io.github.sporklibrary.internal.AnnotatedMethods;

import static io.github.sporklibrary.test.ClassAsserts.assertUtilityClassWellDefined;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests {

	@Test
	public void test() throws Exception {
		assertUtilityClassWellDefined(AnnotatedMethods.class);
		assertUtilityClassWellDefined(AnnotatedFields.class);
	}
}
