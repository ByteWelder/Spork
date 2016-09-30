package io.github.sporklibrary.interfaces;

import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.interfaces.MethodBinder;
import io.github.sporklibrary.interfaces.TypeBinder;

public interface BinderRegistry {
	/**
	 * Register a FieldBinder
	 *
	 * @param fieldBinder the binder instance
	 */
	void register(FieldBinder<?> fieldBinder);

	/**
	 * Register a MethodBinder
	 *
	 * @param methodBinder the binder instance
	 */
	void register(MethodBinder<?> methodBinder);

	/**
	 * Register a TypeBinder
	 *
	 * @param typeBinder the binder instance
	 */
	void register(TypeBinder<?> typeBinder);
}
