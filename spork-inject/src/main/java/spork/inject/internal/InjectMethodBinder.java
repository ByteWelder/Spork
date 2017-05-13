package spork.inject.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import spork.exceptions.BindFailed;
import spork.exceptions.ExceptionMessageBuilder;
import spork.exceptions.UnexpectedException;
import spork.extension.MethodBinder;
import spork.inject.internal.reflection.Classes;

public final class InjectMethodBinder implements MethodBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, Inject annotation, Method method, Object... parameters) throws BindFailed {
		ObjectGraphImpl objectGraph = Classes.findFirstInstanceOfType(ObjectGraphImpl.class, parameters);
		if (objectGraph == null) {
			String message = new ExceptionMessageBuilder("No ObjectGraph specified in instance arguments of bind()")
					.annotation(Inject.class)
					.suggest("call Spork.bind(target, objectGraph")
					.bindingInto(method)
					.build();

			throw new BindFailed(message);
		}

		try {
			method.setAccessible(true);
			Object[] invocationParameters = objectGraph.getInjectableMethodParameters(method);
			method.invoke(object, invocationParameters);
		} catch (IllegalAccessException caught) {
			String message = new ExceptionMessageBuilder("Failed to access a Method that was previously made accessible. ")
					.suggest("There might be a concurrency issue.")
					.annotation(Inject.class)
					.bindingInto(method)
					.build();
			throw new UnexpectedException(message, caught);
		} catch (ObjectGraphException caught) {
			String message = new ExceptionMessageBuilder("Failed to resolve object in ObjectGraph")
					.annotation(Inject.class)
					.bindingInto(method)
					.build();
			throw new BindFailed(message, caught);
		} catch (InvocationTargetException caught) {
			String message = new ExceptionMessageBuilder("Failed to invoke injection method")
					.annotation(Inject.class)
					.bindingInto(method)
					.build();
			throw new BindFailed(message, caught);
		} finally {
			method.setAccessible(false);
		}
	}
}
