package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import spork.inject.Provides;

/**
 * Finds methods given a target type with zero or more annotations.
 * Finds and caches Methods in a cache for all known types.
 * It keeps a MethodCache for each type that is passed as a module.
 */
public class ModuleMethodRetriever {
	private final Map<Class<?>, MethodCache> classMethodCacheMap = new HashMap<>();

	/**
	 * Find a method for a module that matches the target criteria for the specified target.
	 * The target is generally a Field or a Parameter.
	 *
	 * Methods must be specified by the {@link spork.inject.Provides} annotation in the module.
	 *
	 * @param targetType isAssignableTo(type) will be called on the return types to find a match in the interface
	 * @param targetAnnotations the annotations that might define the Qualifier of the injection
	 * @param module an object instance that is annotated with {@link spork.inject.Module}
	 * @return the method or null
	 */
	public @Nullable Method findMethod(Class<?> targetType, Annotation[] targetAnnotations, Object module) {
		Class<?> moduleClass = module.getClass();

		// Go through all classes in the inheritance tree
		while (moduleClass != null && moduleClass != Object.class) {
			// Find the cached method list
			MethodCache methodCache = classMethodCacheMap.get(module.getClass());

			// If there is no cached method list, create one
			if (methodCache == null) {
				methodCache = createMethodCache(moduleClass);
				classMethodCacheMap.put(moduleClass, methodCache);
			}

			// Go through each method and find the one that matches
			String methodSignature = InjectSignatureFactory.createSignature(targetType, targetAnnotations);
			Method method = methodCache.get(methodSignature);

			if (method != null) {
				// Return the method that was found
				return method;
			} else {
				// Continue with the next class in the hierarchy
				moduleClass = moduleClass.getSuperclass();
			}
		}

		return null;
	}

	private MethodCache createMethodCache(Class<?> moduleClass) {
		List<Method> methodList = getMethods(moduleClass);
		MethodCache cache = new MethodCache();
		for (Method method : methodList) {
			String signature = InjectSignatureFactory.createSignature(method);
			cache.put(signature, method);
		}
		return cache;
	}

	/**
	 * Find all methods for a specific module class
	 * @param moduleClass the module class to look for methods
	 * @return a non-mutable list which contains 0 or more methods
	 */
	private static List<Method> getMethods(Class<?> moduleClass) {
		List<Method> methods = new ArrayList<>();

		for (Method method : moduleClass.getMethods()) {
			if (method.getAnnotation(Provides.class) != null) {
				methods.add(method);
			}
		}

		return methods.isEmpty() ? Collections.<Method>emptyList() : methods;
	}
}
