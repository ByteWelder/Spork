package io.github.sporklibrary.test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.reflection.AnnotatedFields;
import io.github.sporklibrary.reflection.AnnotatedMethods;
import org.junit.Test;

import static io.github.sporklibrary.test.ClassAsserts.assertAnnotationDefaultClassWellDefined;
import static io.github.sporklibrary.test.ClassAsserts.assertUtilityClassWellDefined;

public class ClassTests
{
	@Test
	public void spork() throws Exception
	{
		assertUtilityClassWellDefined(Spork.class);
	}

	@Test
	public void annotatedMethods() throws Exception
	{
		assertUtilityClassWellDefined(AnnotatedMethods.class);
	}

	@Test
	public void annotatedFields() throws Exception
	{
		assertUtilityClassWellDefined(AnnotatedFields.class);
	}

	@Test
	public void bindComponentDefault() throws Exception
	{
		assertAnnotationDefaultClassWellDefined(BindComponent.Default.class);
	}
}
