package spork.inject.internal;

import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.internal.objectgraph.ObjectGraphMethodInvoker;
import spork.inject.internal.objectgraph.ObjectGraphs;
import spork.interfaces.MethodBinder;

public class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, Inject annotation, Method method, Object[] parameters) {
		ObjectGraph objectGraph = ObjectGraphs.findObjectGraph(parameters);
		if (objectGraph == null) {
			throw new BindException(Inject.class, object.getClass(), method, "no ObjectGraph specified in instance arguments of Spork.bind() when injecting " + object.getClass().getName());
		}

		ObjectGraphMethodInvoker methodInvoker = new ObjectGraphMethodInvoker(objectGraph);
		methodInvoker.invoke(object, method);
	}
}
