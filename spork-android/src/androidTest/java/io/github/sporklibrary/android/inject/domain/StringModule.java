package io.github.sporklibrary.android.inject.domain;

import io.github.sporklibrary.annotations.Provides;

public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
