package io.github.sporklibrary.android.inject.domain;

import io.github.sporklibrary.annotations.Provides;

public class IntegerModule {
	@Provides
	public Integer integerValue() {
		return 1;
	}
}
