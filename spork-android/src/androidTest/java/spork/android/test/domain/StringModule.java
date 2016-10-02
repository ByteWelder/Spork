package spork.android.test.domain;

import spork.injection.Provides;

public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
