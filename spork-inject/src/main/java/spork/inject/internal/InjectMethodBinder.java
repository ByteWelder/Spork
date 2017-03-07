package spork.inject.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.BindException;
import spork.interfaces.MethodBinder;

public class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, Inject annotation, Method method, Object... parameters) {
		ObjectGraph objectGraph = ObjectGraphs.findObjectGraph(parameters);
		if (objectGraph == null) {
			throw new BindException(Inject.class, object.getClass(), method, "no ObjectGraph specified in instance arguments of Spork.bind() when injecting " + object.getClass().getName());
		}

		try {
			method.setAccessible(true);
			Object[] invocationParameters = ObjectGraphs.getInjectableMethodParameters(objectGraph, method);
			if (invocationParameters == null) {
				method.invoke(object);
			} else {
				method.invoke(object, invocationParameters);
			}
		} catch (ObjectGraphException | IllegalAccessException | InvocationTargetException e) {
			String message = "failed to invoke " + method.getDeclaringClass().getName() + "." + method.getName() + "(): " + e.getMessage();
			throw new BindException(Inject.class, method.getDeclaringClass(), message, e);
		} finally {
			try {
				method.setAccessible(false);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
}
