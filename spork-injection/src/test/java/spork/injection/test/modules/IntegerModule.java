package spork.injection.test.modules;

import spork.injection.Provides;

public class IntegerModule {
	@Provides
	public Integer integerValue() {
		return 1;
	}
}
