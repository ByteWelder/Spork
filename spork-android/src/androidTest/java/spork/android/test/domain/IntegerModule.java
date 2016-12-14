package spork.android.test.domain;

import spork.inject.Provides;

public class IntegerModule {
	@Provides
	public Integer integerValue() {
		return 1;
	}
}
