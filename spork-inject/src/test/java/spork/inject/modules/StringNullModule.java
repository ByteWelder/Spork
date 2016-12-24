package spork.inject.modules;

import javax.annotation.Nullable;

import spork.inject.Module;
import spork.inject.Provides;

@Module
public class StringNullModule {
	@Provides
	@Nullable
	public String stringValue() {
		return null;
	}
}
