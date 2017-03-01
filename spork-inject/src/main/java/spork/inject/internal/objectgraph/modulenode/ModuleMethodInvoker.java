package spork.inject.internal.objectgraph.modulenode;


import java.lang.reflect.Method;

import spork.inject.internal.objectgraph.ObjectGraphMethodInvoker;
import spork.inject.internal.objectgraph.ObjectGraph;

public class ModuleMethodInvoker<T> extends ObjectGraphMethodInvoker<T> {
	private final Object module;
	private final Method method;
	private final Class<?> targetType;

	ModuleMethodInvoker(ObjectGraph objectGraph, Object module, Method method, Class<T> targetType) {
		super(objectGraph);
		this.module = module;
		this.method = method;
		this.targetType = targetType;
	}

	public T invoke() {
		return invoke(module, method, targetType);
	}
}
