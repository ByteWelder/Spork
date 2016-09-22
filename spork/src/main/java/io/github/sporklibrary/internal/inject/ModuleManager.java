package io.github.sporklibrary.internal.inject;

import java.lang.reflect.Method;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.internal.Callable;

public class ModuleManager {
	private final ModuleMethodRetriever moduleMethodRetriever = new ModuleMethodRetriever();

	public @Nullable Callable<?> getCallable(Object[] modules, Class<?> type) {
		for (Object module : modules) {
			Callable callable = getCallable(module, type);

			if (callable != null) {
				return callable;
			}
		}

		return null;
	}

	private @Nullable Callable<?> getCallable(final Object module, Class<?> type) {
		final Method method = moduleMethodRetriever.getMethod(module, type);

		if (method == null) {
			return null;
		}

		return new Callable() {
			@Override
			public Object call() {
				try {
					return method.invoke(module, (Object[])null);
				} catch (IllegalAccessException e) {
					throw new RuntimeException("method \"" + method.getName() + "\" on module " + module.getClass().getName() + " is not public", e);
				} catch (Exception e) {
					throw new RuntimeException("failed to invoke method \"" + method.getName() + "\" on module " + module.getClass().getName(), e);
				}
			}
		};
	}
}
