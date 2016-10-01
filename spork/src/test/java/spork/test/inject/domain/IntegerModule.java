package spork.test.inject.domain;

import spork.annotations.Provides;

public class IntegerModule {
	@Provides
	public Integer integerValue() {
		return 1;
	}
}
