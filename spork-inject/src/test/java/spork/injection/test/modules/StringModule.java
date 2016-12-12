package spork.injection.test.modules;

import spork.injection.Provides;

public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
