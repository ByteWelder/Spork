package spork.injection.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Singleton;

import spork.internal.Callable;

public class ModuleManager {
	private final ModuleMethodRetriever moduleMethodRetriever = new ModuleMethodRetriever();
	private final Map<String, Object> singletonInstanceMap = new HashMap<>();

	public @Nullable spork.internal.Callable<?> getCallable(Object[] modules, Class<?> type) {
		for (Object module : modules) {
			spork.internal.Callable callable = getCallable(module, type);

			if (callable != null) {
				return callable;
			}
		}

		return null;
	}

	private @Nullable Callable<?> getCallable(final Object module, final Class<?> type) {
		final Method method = moduleMethodRetriever.getMethod(module, type);

		if (method == null) {
			return null;
		}

		return new spork.internal.Callable() {
			@Override
			public Object call() {
				try {
					boolean isSingleton = method.getAnnotation(Singleton.class) != null;

					if (!isSingleton) {
						return method.invoke(module, (Object[]) null);
					} else {
						String typeName = method.getReturnType().getName();
						Object instance = singletonInstanceMap.get(typeName);

						if (instance == null) {
							instance = method.invoke(module, (Object[]) null);
							singletonInstanceMap.put(typeName, instance);
						}

						return instance;
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException("method \"" + method.getName() + "\" on module " + module.getClass().getName() + " is not public", e);
				} catch (Exception e) {
					throw new RuntimeException("failed to invoke method \"" + method.getName() + "\" on module " + module.getClass().getName(), e);
				}
			}
		};
	}
}
