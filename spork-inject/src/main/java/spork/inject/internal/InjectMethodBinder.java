package spork.inject.internal;

import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.extension.MethodBinder;
import spork.inject.internal.reflection.Classes;
import spork.internal.Reflection;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

public class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	@SuppressWarnings("PMD.PreserveStackTrace")
	public void bind(Object object, Inject annotation, Method method, Object... parameters) {
		ObjectGraphImpl objectGraph = Classes.findFirstInstanceOfType(ObjectGraphImpl.class, parameters);
		if (objectGraph == null) {
			throw bindFailedBuilder(Inject.class, "no ObjectGraph specified in instance arguments of bind()")
					.into(method)
					.build();
		}

		try {
			Object[] invocationParameters = objectGraph.getInjectableMethodParameters(method);
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
