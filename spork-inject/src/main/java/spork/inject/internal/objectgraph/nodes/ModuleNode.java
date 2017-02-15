package spork.inject.internal.objectgraph.nodes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import spork.inject.internal.objectgraph.Annotations;
import spork.inject.internal.objectgraph.InjectSignature;
import spork.inject.internal.objectgraph.Modules;
import spork.inject.internal.objectgraph.Nullability;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphNode;

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
			Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, method);
			InjectSignature key = new InjectSignature(method.getReturnType(), nullability, qualifierAnnotation);
			methodCache.put(key, method);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Provider<T> findProvider(ObjectGraph objectGraph, InjectSignature injectSignature) {
		Method method = methodCache.get(injectSignature);

		if (method == null) {
			return null;
		}

		// code that can invoke a method on the module to create an instance
		ModuleMethodInvoker moduleMethodInvoker = new ModuleMethodInvoker(objectGraph, module, method, injectSignature.getType());

		boolean isSingleton = method.getAnnotation(Singleton.class) != null;

		if (isSingleton) {
			// TODO: implement custom qualifiers
			return new SingletonInstanceProvider(injectSignature, moduleMethodInvoker, scopedInstanceCache);
		} else if (injectSignature.getQualifierAnnotation() != null) {
				return new ScopedInstanceProvider(injectSignature, moduleMethodInvoker, scopedInstanceCache);
		} else {
			return new ScopelessInstanceProvider(moduleMethodInvoker);
		}
	}
}
