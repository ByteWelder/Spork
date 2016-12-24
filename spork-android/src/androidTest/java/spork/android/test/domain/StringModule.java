package spork.android.test.domain;

import android.support.test.espresso.core.deps.dagger.Module;

import spork.inject.Provides;

@Module
public class StringModule {
	@Provides
	public String stringValue() {
		return "test";
	}
}
