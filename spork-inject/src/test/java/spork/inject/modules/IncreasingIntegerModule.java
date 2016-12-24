package spork.inject.modules;

import spork.inject.Module;
import spork.inject.Provides;

@Module
public class IncreasingIntegerModule {
	private int counter = 0;

	@Provides
	public Integer integerValue() {
		return ++counter;
	}
}
