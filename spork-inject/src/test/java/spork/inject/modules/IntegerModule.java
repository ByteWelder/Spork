package spork.inject.modules;

import spork.inject.Module;
import spork.inject.Provides;

@Module
public class IntegerModule {
	@Provides
	public Integer integerValue() {
		return 1;
	}
}
