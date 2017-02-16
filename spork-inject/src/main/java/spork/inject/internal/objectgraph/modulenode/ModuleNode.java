package spork.inject.internal.objectgraph.modulenode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;
import javax.inject.Scope;

import spork.inject.Provides;
import spork.inject.internal.InjectSignature;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphNode;
import spork.inject.internal.objectgraph.modulenode.providers.CachedProvider;
import spork.inject.internal.objectgraph.modulenode.providers.InstanceCacheProvider;

public class ModuleNode implements ObjectGraphNode {
	private final Object module;
	private final Map<InjectSignature, Method> methodCache;
	private final InstanceCache instanceCache;

	public ModuleNode(Object module, InstanceCache instanceCache) {
		Method[] methods = module.getClass().getMethods();

		this.module = module;
		this.instanceCache = instanceCache;
		this.methodCache = new HashMap<>(methods.length);

		// find all relevant methods and store them with their respective keys into the method cache
		for (Method method : methods) {
			// find a matching method
			if (method.isAnnotationPresent(Provides.class)) {
				// create key
				Nullability nullability = Nullability.create(method);
				Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, method);
				InjectSignature key = new InjectSignature(method.getReturnType(), nullability, qualifierAnnotation);
				// store method
				methodCache.put(key, method);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> Provider<T> findProvider(ObjectGraph objectGraph, InjectSignature injectSignature) {
		Method method = methodCache.get(injectSignature);

		if (method == null) {
			return null;
		}

		ModuleMethodInvoker<T> moduleMethodInvoker = new ModuleMethodInvoker(objectGraph, module, method, injectSignature.getType());
		Annotation scopeAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, method);

		// No scope and no qualifier means a new instance per injection
		if (scopeAnnotation == null && injectSignature.getQualifierAnnotation() == null) {
			return new CachedProvider<>(moduleMethodInvoker);
		} else {
			return new InstanceCacheProvider<>(injectSignature, moduleMethodInvoker, instanceCache, scopeAnnotation);
		}
	}
}
