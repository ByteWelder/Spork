package spork.android.test.domain;

import android.support.test.espresso.core.deps.dagger.Module;

import javax.annotation.Nullable;

import spork.inject.Provides;

@Module
public class StringNullModule {
	@Provides
	@Nullable
	public String stringValue() {
		return null;
	}
}
