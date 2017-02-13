package spork.inject.internal.objectgraph.nodes;


import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.exceptions.BindException;

public class MethodInvoker<T> {
	private Class<?> targetType;
	private Object parent;
	private Method method;

	MethodInvoker(Class<T> targetType, Object parent, Method method) {
		this.targetType = targetType;
		this.parent = parent;
		this.method = method;
	}

	public T invoke() {
		@Nullable Object[] methodParameters = collectMethodParameters(method);
		return invokeMethod(methodParameters);
	}

	@SuppressWarnings("unchecked")
	private T invokeMethod(@Nullable Object[] params) {
		try {
			return (T) method.invoke(parent, params);
		} catch (IllegalAccessException e) {
			throw new BindException(Inject.class, parent.getClass(), targetType, "method \"" + method.getName() + "\" on module " + parent.getClass().getName() + " is not public", e);
		} catch (Exception e) {
			throw new BindException(Inject.class, parent.getClass(), targetType, "failed to invoke method \"" + method.getName() + "\" on module " + parent.getClass().getName(), e);
		}
	}

	private @Nullable Object[] collectMethodParameters(Method method) {
		int parameterCount = method.getParameterTypes().length;
		if (parameterCount == 0) {
			return null;
		} else {
			// TODO: implement support for multiple arguments
			return new Object[parameterCount];
		}
	}
}
