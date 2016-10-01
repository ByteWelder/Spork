package spork.test.inject.domain;

import spork.annotations.Nullable;
import spork.annotations.Provides;

public class StringNullModule {
	@Provides
	@Nullable
	public String stringValue() {
		return null;
	}
}
