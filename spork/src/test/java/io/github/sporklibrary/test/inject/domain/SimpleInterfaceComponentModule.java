package io.github.sporklibrary.test.inject.domain;

import io.github.sporklibrary.annotations.Provides;

public class SimpleInterfaceComponentModule {
	@Provides
	public Runnable runnable() {
		return new SimpleInterfaceComponent();
	}
}
