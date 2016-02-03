package io.github.sporklibrary.test.components.bindparent.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.ComponentParent;
import io.github.sporklibrary.annotations.ComponentScope;

public class FaultyComponents
{
	public static class NotAnnotatedParent
	{
		@BindComponent
		private NotAnnotatedChild mChild;

		public NotAnnotatedParent()
		{
			Spork.bind(this);
		}
	}

	public static class NotAnnotatedChild
	{
		public NotAnnotatedChild(NotAnnotatedParent parent)
		{
		}
	}

	public static class TypeMismatchParent
	{
		@BindComponent
		private TypeMismatchChild mChild;

		public TypeMismatchParent()
		{
			Spork.bind(this);
		}
	}

	public static class TypeMismatchChild
	{
		public TypeMismatchChild(@ComponentParent String parent)
		{
		}
	}

	public static class SingletonParent
	{
		@BindComponent
		private SingletonChild mChild;

		public SingletonParent()
		{
			Spork.bind(this);
		}
	}

	@ComponentScope(ComponentScope.Scope.SINGLETON)
	public static class SingletonChild
	{
		public SingletonChild(@ComponentParent SingletonParent parent)
		{
		}
	}

	public static class MultiArgumentParent
	{
		@BindComponent
		private MultiArgumentChild mChild;

		public MultiArgumentParent()
		{
			Spork.bind(this);
		}
	}

	@ComponentScope(ComponentScope.Scope.SINGLETON)
	public static class MultiArgumentChild
	{
		public MultiArgumentChild(@ComponentParent MultiArgumentParent parent1, @ComponentParent MultiArgumentParent parent2)
		{
		}
	}
}
