package spork.inject.internal.objectgraph.nodes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import spork.inject.internal.objectgraph.InjectSignature;
import spork.inject.internal.objectgraph.Modules;
import spork.inject.internal.objectgraph.Nullability;
import spork.inject.internal.objectgraph.ObjectGraphNode;
import spork.inject.internal.objectgraph.Scopes;

public class ModuleNode implements ObjectGraphNode {
	private final Object module;
	private final Map<InjectSignature, Method> methodCache;
	private final ScopedInstanceCache scopedInstanceCache;

	public ModuleNode(Object module, ScopedInstanceCache scopedInstanceCache) {
		this.module = module;
		this.scopedInstanceCache = scopedInstanceCache;

		List<Method> methods = Modules.getProvidesMethods(module.getClass());
		methodCache = new HashMap<>(methods.size());

		// cache each method
		for (Method method : methods) {
			Nullability nullability = Nullability.create(method);
			Annotation scopeAnnotation = Scopes.findScopeAnnotation(method);
			InjectSignature key = new InjectSignature(method.getReturnType(), nullability, scopeAnnotation);
			methodCache.put(key, method);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Provider<T> findProvider(InjectSignature injectSignature) {
		Method method = methodCache.get(injectSignature);

		if (method == null) {
			return null;
		}

		// code that can invoke a method on the module to create an instance
		MethodInvoker methodInvoker = new MethodInvoker(injectSignature.getClass(), module, method);
		boolean isSingleton = method.getAnnotation(Singleton.class) != null;

		if (isSingleton) {
			return new SingletonInstanceProvider(injectSignature, methodInvoker, scopedInstanceCache);
		} else if (injectSignature.getScopeAnnotation() != null) {
			return new ScopedInstanceProvider(injectSignature, methodInvoker, scopedInstanceCache);
		} else {
			return new ScopelessInstanceProvider(methodInvoker);
		}
	}
}
