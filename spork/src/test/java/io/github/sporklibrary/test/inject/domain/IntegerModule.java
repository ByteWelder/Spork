package io.github.sporklibrary.test.inject.domain;

import io.github.sporklibrary.annotations.Provides;

public class IntegerModule {
	@Provides
	public Integer integerValue() {
		return 1;
	}
}
