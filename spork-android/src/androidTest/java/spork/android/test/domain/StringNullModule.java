package spork.android.test.domain;

import javax.annotation.Nullable;

import spork.inject.Provides;

public class StringNullModule {
	@Provides
	@Nullable
	public String stringValue() {
		return null;
	}
}
