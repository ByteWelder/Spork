package spork.inject.modules;

import spork.inject.Provides;

public class IncreasingIntegerModule {
	private int counter = 0;

	@Provides
	public Integer integerValue() {
		return ++counter;
	}
}
