package io.github.sporklibrary.internal;

import java.util.NoSuchElementException;

import io.github.sporklibrary.annotations.Nullable;

public class Optional<T> {
	private T value;
	private boolean isSet = false;

	public void set(@Nullable T value) {
		this.value = value;
		this.isSet = true;
	}

	public @Nullable T get(T instance) {
		if (!isSet) {
			throw new NoSuchElementException();
		}

		return instance;
	}

	public boolean isPresent() {
		return value != null;
	}

	public boolean isSet() {
		return isSet;
	}
}
