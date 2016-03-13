package io.github.sporklibrary.test.components.mocking;

import io.github.sporklibrary.binders.component.ComponentInstanceManager;
import io.github.sporklibrary.binders.component.factories.MockingComponentFactory;
import io.github.sporklibrary.test.components.mocking.domain.MockedImplementation;
import io.github.sporklibrary.test.components.mocking.domain.ParentComponent;
import io.github.sporklibrary.test.components.mocking.domain.RegularImplementation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MockingTests
{
	@Test
	public void test()
	{
		ParentComponent regular_instance = new ParentComponent();
		assertEquals("default binding", 1, regular_instance.getCommonInterface().get());

		registerMockingClasses();

		ParentComponent mocked_instance = new ParentComponent();
		assertEquals("mocked binding", 2, mocked_instance.getCommonInterface().get());
	}

	private void registerMockingClasses()
	{
		MockingComponentFactory mocking_factory = new MockingComponentFactory()
			.register(RegularImplementation.class, MockedImplementation.class);

		ComponentInstanceManager.setComponentFactory(mocking_factory);
	}
}
