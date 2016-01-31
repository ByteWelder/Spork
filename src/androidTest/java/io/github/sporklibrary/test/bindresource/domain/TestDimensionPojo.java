package io.github.sporklibrary.test.bindresource.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindResource;
import io.github.sporklibrary.test.R;

public class TestDimensionPojo
{
	@BindResource(R.dimen.spork_test_dimension)
	private float mTest;

	public TestDimensionPojo()
	{
		Spork.bind(this);
	}
}
