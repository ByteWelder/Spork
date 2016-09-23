package io.github.sporklibrary.test.inject.domain;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.annotations.Provides;

public class StringNullModule {
	@Provides
	@Nullable
	public String stringValue() {
		return null;
	}
}
