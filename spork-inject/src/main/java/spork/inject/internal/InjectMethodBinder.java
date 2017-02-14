package spork.inject.internal;

import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.interfaces.MethodBinder;

public class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, Inject annotation, Method method, Object[] parameters) {
		// Bind with module system (uses @Provides annotation on methods)
		if (parameters.length != 1 || !(parameters[0] instanceof ObjectGraph)) {
			throw new BindException(Inject.class, object.getClass(), method, "must specify a single ObjectGraph instance arguments in Spork.bind(instance, ...) when using @Inject at " + method.getName());
		}

		ObjectGraph objectGraph = (ObjectGraph) parameters[0];

		// TODO: implement
	}
}
