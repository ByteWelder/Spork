package io.github.sporklibrary.test.inject.domain;

import io.github.sporklibrary.annotations.Provides;

public class SimpleComponentModule {
	@Provides
	public SimpleComponent simpleComponent() {
		return new SimpleComponent();
	}
}
