package spork.android.test.domain;

import spork.annotations.Provides;

public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
