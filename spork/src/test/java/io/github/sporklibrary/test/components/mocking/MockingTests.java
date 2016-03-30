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
		ParentComponent regularInstance = new ParentComponent();
		assertEquals("default binding", 1, regularInstance.getCommonInterface().get());

		registerMockingClasses();

		ParentComponent mockedInstance = new ParentComponent();
		assertEquals("mocked binding", 2, mockedInstance.getCommonInterface().get());
	}

	private void registerMockingClasses()
	{
		MockingComponentFactory mockingFactory = new MockingComponentFactory()
			.register(RegularImplementation.class, MockedImplementation.class);

		ComponentInstanceManager.setComponentFactory(mockingFactory);
	}
}
