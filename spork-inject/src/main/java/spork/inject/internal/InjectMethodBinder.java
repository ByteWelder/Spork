package spork.inject.internal;

import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.interfaces.MethodBinder;
import spork.internal.Reflection;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

public class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, Inject annotation, Method method, Object... parameters) {
		ObjectGraph objectGraph = ObjectGraphs.findObjectGraph(parameters);
		if (objectGraph == null) {
			throw bindFailedBuilder(Inject.class, "no ObjectGraph specified in instance arguments of Spork.bind()")
					.into(method)
					.build();
		}

		try {
			Object[] invocationParameters = ObjectGraphs.getInjectableMethodParameters(objectGraph, method);
			Reflection.invokeMethod(Inject.class, method, object, invocationParameters);
		} catch (ObjectGraphException e) {
			String message = "failed to resolve object in ObjectGraph: " + e.getMessage();
			throw bindFailedBuilder(Inject.class, message)
					.into(method)
					.cause(e)
					.build();
		}
	}
}
