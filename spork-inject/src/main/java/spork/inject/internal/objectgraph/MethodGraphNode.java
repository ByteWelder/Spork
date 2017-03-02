package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Scope;

import spork.BindException;
import spork.inject.AnnotationSerializers;
import spork.inject.internal.InjectSignature;
import spork.inject.internal.lang.Annotations;

class MethodGraphNode implements ObjectGraphNode {
	private final InjectSignature injectSignature;
	private final Object parent;
	private final Method method;
	@Nullable private final String scopeId;

	MethodGraphNode(InjectSignature injectSignature, Object parent, Method method) {
		this.injectSignature = injectSignature;
		this.parent = parent;
		this.method = method;
		this.scopeId = findScopeId(method);
	}

	@Override
	public InjectSignature getInjectSignature() {
		return injectSignature;
	}

	@Override
	@Nullable
	public String getScopeId() {
		return scopeId;
	}

	@Override
	public Object resolve(Object... arguments) {
		method.setAccessible(true);
		try {
			return method.invoke(parent, arguments);
		} catch (InvocationTargetException e) {
			throw new BindException(Inject.class, "Failed to invoke method \"" + method.getName() + "\" on " + parent.getClass().getName());
		} catch (IllegalAccessException e) {
			throw new BindException(Inject.class, "Failed to invoke method \"" + method.getName() + "\" on " + parent.getClass().getName() + ": " + e.getMessage());
		} finally {
			try {
				method.setAccessible(false);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	@Nullable
	@Override
	public Object[] collectParameters(ObjectGraph objectGraph) {
		try {
			return objectGraph.collectParameters(method.getParameterTypes(), method.getParameterAnnotations(), method.getGenericParameterTypes());
		} catch (ObjectGraphException e) {
			String message = "failed to call " + method.getDeclaringClass().getName() + "." + method.getName() + "(): " + e.getMessage();
			throw new BindException(Inject.class, method.getDeclaringClass(), injectSignature.getType(), message);
		}
	}

	private String findScopeId(Method method) {
		Annotation annotation = Annotations.findAnnotationAnnotatedWith(Scope.class, method);
		if (annotation != null) {
			return AnnotationSerializers.serialize(annotation);
		} else {
			return null;
		}
	}
}
