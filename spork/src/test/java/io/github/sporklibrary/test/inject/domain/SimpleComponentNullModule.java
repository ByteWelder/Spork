package io.github.sporklibrary.test.inject.domain;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.annotations.Provides;

public class SimpleComponentNullModule {
	@Provides
	public @Nullable SimpleComponent simpleComponent() {
		return null;
	}
}
