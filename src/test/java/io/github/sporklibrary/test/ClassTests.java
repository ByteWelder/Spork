package io.github.sporklibrary.test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.reflection.AnnotatedFields;
import io.github.sporklibrary.reflection.AnnotatedMethods;
import org.junit.Test;

import static io.github.sporklibrary.test.ClassAsserts.assertAnnotationDefaultClassWellDefined;
import static io.github.sporklibrary.test.ClassAsserts.assertUtilityClassWellDefined;

/**
 * Test functionality on specific classes such as utility classes.
 */
public class ClassTests
{
	@Test
	public void test() throws Exception
	{
		assertUtilityClassWellDefined(Spork.class);
		assertUtilityClassWellDefined(AnnotatedMethods.class);
		assertUtilityClassWellDefined(AnnotatedFields.class);

		assertAnnotationDefaultClassWellDefined(BindComponent.Default.class);
	}
}
