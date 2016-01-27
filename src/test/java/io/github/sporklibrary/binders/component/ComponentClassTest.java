package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.Component;
import io.github.sporklibrary.components.scope.DefaultScopedComponent;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ComponentClassTest
{
	@Test
	public void properties()
	{
		DefaultScopedComponent component = new DefaultScopedComponent();

		Component component_annotation = component.getClass().getAnnotation(Component.class);

		assertNotNull(component_annotation);

		ComponentClass component_class = new ComponentClass(component_annotation, component.getClass());

		assertNotNull(component_class.getComponent());
		assertNotNull(component_class.getDeclaredClass());
	}
}
