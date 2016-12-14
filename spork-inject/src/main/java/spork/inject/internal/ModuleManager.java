package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Provider;

public class ModuleManager {
	private final ModuleMethodRetriever moduleMethodRetriever = new ModuleMethodRetriever();
	private final SingletonCache singletonCache = new SingletonCache();

	public @Nullable <T> Provider<T> getProvider(Field field, Object[] modules, Class<T> type) {
		for (Object module : modules) {
			Provider<T> provider = getProvider(field, module, type);

			if (provider != null) {
				return provider;
			}
		}

		return null;
	}

	private @Nullable <T> Provider<T> getProvider(final Field field, final Object module, final Class<T> type) {
		final Method method = moduleMethodRetriever.getMethod(module, type);

		if (method == null) {
			return null;
		}

		return new InstanceProvider<>(field, module, method, singletonCache);
	}
}
