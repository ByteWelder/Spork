package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Provider;

import spork.inject.Module;

public class ModuleManager {
	private final ModuleMethodRetriever moduleMethodRetriever;
	private final SingletonCache singletonCache;

	public ModuleManager(ModuleMethodRetriever moduleMethodRetriever, SingletonCache singletonCache) {
		this.moduleMethodRetriever = moduleMethodRetriever;
		this.singletonCache = singletonCache;
	}

	public @Nullable <T> Provider<T> getProvider(Field targetField, Class<T> targetType, Object[] parameters) {
		for (Object module : parameters) {
			if (module.getClass().getAnnotation(Module.class) == null) {
				continue;
			}

			Provider<T> provider = getProvider(targetType, targetField, module);

			if (provider != null) {
				return provider;
			}
		}

		return null;
	}

	private @Nullable <T> Provider<T> getProvider(Class<T> targetType, Field targetField, final Object module) {
		Method method = moduleMethodRetriever.findMethod(targetType, targetField.getAnnotations(), module);

		if (method == null) {
			return null;
		}

		return new InstanceProvider<>(targetField, module, method, singletonCache);
	}
}
