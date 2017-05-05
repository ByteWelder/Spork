package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Scope;

import spork.exceptions.ExceptionMessageBuilder;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.reflection.InjectSignature;

/**
 * A node represents a provider (@Provides method) from a module.
 */
public final class ObjectGraphNode {
	private final InjectSignature injectSignature;
	private final Object parent;
	private final Method method;
	@Nullable private final Annotation scopeAnnotation;

	ObjectGraphNode(InjectSignature injectSignature, Object parent, Method method) {
		this.injectSignature = injectSignature;
		this.parent = parent;
		this.method = method;
		this.scopeAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, method); // TODO: cache?
	}

	public InjectSignature getInjectSignature() {
		return injectSignature;
	}

	@Nullable
	Annotation getScope() {
		return scopeAnnotation;
	}

	public Object resolve(Object... arguments) throws ObjectGraphException {
		try {
			return method.invoke(parent, arguments);
		} catch (IllegalAccessException e) {
			String message = getErrorMessage("Not allowed to invoke " + method.toString())
					.suggest("Ensure the class and method are both accessible (e.g. public)")
					.build();
			throw new ObjectGraphException(message, e);
		} catch (InvocationTargetException e) {
			String message = getErrorMessage("Failed to invoke " + method.toString()).build();
			throw new ObjectGraphException(message, e);
		}
	}

	@Nullable
	Object[] collectParameters(ObjectGraphImpl objectGraph) throws ObjectGraphException {
		try {
			return objectGraph.getInjectableMethodParameters(method);
		} catch (ObjectGraphException e) {
			String message = getErrorMessage("Failed to invoke " + method.toString()).build();
			throw new ObjectGraphException(message, e);
		}
	}

	private ExceptionMessageBuilder getErrorMessage(String message) {
		return new ExceptionMessageBuilder(message)
				.annotation(Inject.class)
				.bindingFrom(method)
				.bindingInto(injectSignature.toString());
	}
}
