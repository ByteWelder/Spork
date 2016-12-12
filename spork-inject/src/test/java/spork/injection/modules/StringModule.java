package spork.injection.modules;

import spork.injection.Provides;

public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
