package spork.injection.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import spork.injection.Provides;

public class ModuleMethodRetriever {
	private final Map<Class<?>, List<Method>> classMethodListMap = new HashMap<>();

	/**
	 * Find a method with a specific return type that is annotated with @Provides in the given class
	 * @param module an object instance
	 * @param type isAssignableTo(type) will be called on the return types to find a match in the interface
	 * @return the method or null
	 */
	public @Nullable Method getMethod(Object module, Class<?> type) {
		Class<?> moduleClass = module.getClass();

		// Go through all classes in the inheritance tree
		while (moduleClass != null && moduleClass != Object.class) {
			// Find the cached method list
			List<Method> methodList = classMethodListMap.get(module.getClass());

			// If there is no cached method list, create one
			if (methodList == null) {
				methodList = ModuleMethodRetriever.findMethods(moduleClass);
				classMethodListMap.put(moduleClass, methodList);
			}

			// Go through each method and find the one that matches
			Method method = ModuleMethodRetriever.findMethod(methodList, type);

			// Return the method that was found
			if (method != null) {
				return method;
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
	private static List<Method> findMethods(Class<?> moduleClass) {
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
	private static @Nullable Method findMethod(List<Method> methodList, Class<?> returnType) {
		for (Method method : methodList) {
			if (returnType.isAssignableFrom(method.getReturnType())) {
				return method;
			}
		}

		return null;
	}
}
