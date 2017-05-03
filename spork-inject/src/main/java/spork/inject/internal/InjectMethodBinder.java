package spork.inject.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.exceptions.BindContext;
import spork.exceptions.BindContextBuilder;
import spork.exceptions.BindFailed;
import spork.extension.MethodBinder;
import spork.inject.internal.reflection.Classes;
import spork.internal.Reflection;

public class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, Inject annotation, Method method, Object... parameters) throws BindFailed {
		ObjectGraphImpl objectGraph = Classes.findFirstInstanceOfType(ObjectGraphImpl.class, parameters);
		if (objectGraph == null) {
			BindContext bindContext = new BindContextBuilder(Inject.class)
					.suggest("call Spork.bind(target, objectGraph")
					.bindingInto(method)
					.build();

			throw new BindFailed("No ObjectGraph specified in instance arguments of bind()", bindContext);
		}

		try {
			Object[] invocationParameters = objectGraph.getInjectableMethodParameters(method);
			Reflection.invokeMethod(method, object, invocationParameters);
		} catch (ObjectGraphException caught) {
			BindContext bindContext = new BindContextBuilder(Inject.class)
					.bindingInto(method)
					.build();
			throw new BindFailed("Failed to resolve object in ObjectGraph: " + caught.getMessage(), caught, bindContext);
		} catch (InvocationTargetException caught) {
			BindContext bindContext = new BindContextBuilder(Inject.class)
					.bindingInto(method)
					.build();
			throw new BindFailed("Failed to invoke injection method", caught, bindContext);
		}
	}
}
