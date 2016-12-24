package spork.inject.modules;

import spork.inject.Module;
import spork.inject.Provides;

@Module
public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
