package io.github.sporklibrary.test.components.bindparent;

import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.components.bindparent.domain.ComponentA;
import io.github.sporklibrary.test.components.bindparent.domain.ComponentB;
import io.github.sporklibrary.test.components.bindparent.domain.ComponentC;
import io.github.sporklibrary.test.components.bindparent.domain.FaultyComponents;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BindParentTests
{
	@Test
	public void test()
	{
		ComponentA root = new ComponentA();

		ComponentB root_child = root.getChild();

		assertNotNull(root_child);
		assertNotNull(root_child.getParent());

		ComponentC root_child_child = root_child.getChild();

		assertNotNull(root_child_child);
		assertNotNull(root_child_child.getParent());
	}

	@Test(expected = BindException.class)
	public void notAnnotated()
	{
		new FaultyComponents.NotAnnotatedParent();
	}

	@Test(expected = BindException.class)
	public void typeMismatch()
	{
		new FaultyComponents.TypeMismatchParent();
	}

	@Test(expected = BindException.class)
	public void singleton()
	{
		new FaultyComponents.SingletonParent();
	}

	@Test(expected = BindException.class)
	public void multiArgument()
	{
		new FaultyComponents.MultiArgumentParent();
	}
}
