package io.github.sporklibrary.test;

import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.utils.*;
import io.github.sporklibrary.utils.support.SupportFragments;
import io.github.sporklibrary.utils.support.SupportLibraries;
import org.junit.Test;

import static io.github.sporklibrary.test.ClassAsserts.assertUtilityClassWellDefined;

@SmallTest
public class ClassTests
{
	@Test
	public void test() throws Exception
	{
		// .utils
		assertUtilityClassWellDefined(ContextResolver.class);
		assertUtilityClassWellDefined(FragmentResolver.class);
		assertUtilityClassWellDefined(ViewResolver.class);
		assertUtilityClassWellDefined(Views.class);
		assertUtilityClassWellDefined(Classes.class);

		// .utils.support
		assertUtilityClassWellDefined(SupportFragments.class);
		assertUtilityClassWellDefined(SupportLibraries.class);
	}
}
