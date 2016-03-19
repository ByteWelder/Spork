package io.github.sporklibrary.test.utils;

import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.utils.Classes;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@SmallTest
public class ClassesTest
{
	@Test
	public void test()
	{
		Class<?> result = Classes.classForNameOrNull("nonexisting");
		assertNull(result);

		result = Classes.classForNameOrNull(getClass().getName());
		assertNotNull(result);
	}
}
