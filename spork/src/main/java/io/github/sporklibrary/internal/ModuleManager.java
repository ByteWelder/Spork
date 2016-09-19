package io.github.sporklibrary.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.annotations.Provides;

public class ModuleManager {
	private final Map<Class<?>, List<Method>> classMethodListMap = new HashMap<>();

	public @Nullable Object getObject(Object[] modules, Class<?> type) {
		for (Object module : modules) {
			Object instance = getObject(module, type);
			if (instance != null) {
				return instance;
			}
		}

		return null;
	}

	public @Nullable Object getObject(Object module, Class<?> type) {

		Class<?> moduleClass = module.getClass();

		while (moduleClass != null && moduleClass != Object.class) {
			List<Method> methodList = classMethodListMap.get(module.getClass());

			if (methodList == null) {
				methodList = findMethods(moduleClass);
				classMethodListMap.put(moduleClass, methodList);
			}

			// Go through each method and find the one that matches
			Method method = findMethod(methodList, type);

			// Invoke the method
			if (method != null) {
				try {
					Object instance = method.invoke(module, (Object[])null);

					if (instance == null) {
						throw new RuntimeException("method \"" + method.getName() + "\" on module " + module.getClass().getName() + " returned null (which is not supported)");
					}

					return instance;
				} catch (IllegalAccessException e) {
					throw new RuntimeException("method \"" + method.getName() + "\" on module " + module.getClass().getName() + " is not public", e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException("failed to invoke method \"" + method.getName() + "\" on module " + module.getClass().getName(), e);
				}
			}

			moduleClass = moduleClass.getSuperclass();
		}

		return null;
	}

	/**
	 * Find all methods for a specific module class
	 * @param moduleClass the module class to look for methods
	 * @return a non-mutable list which contains 0 or more methods
	 */
	private List<Method> findMethods(Class<?> moduleClass) {
		List<Method> methods = new ArrayList<>();

		for (Method method : moduleClass.getMethods()) {
			if (method.getAnnotation(Provides.class) != null) {
				methods.add(method);
			}
		}

		return methods.isEmpty() ? Collections.<Method>emptyList() : methods;
	}

	/**
	 * Find a specific method in a list with a specific return type
	 * @param methodList the list of methods
	 * @param returnType the return type to look for
	 * @return the found method or null
	 */
	private @Nullable Method findMethod(List<Method> methodList, Class<?> returnType) {
		for (Method method : methodList) {
			if (returnType.isAssignableFrom(method.getReturnType())) {
				return method;
			}
		}

		return null;
	}
}
