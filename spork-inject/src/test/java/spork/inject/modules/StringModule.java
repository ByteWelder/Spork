package spork.inject.modules;

import spork.inject.Provides;

public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
