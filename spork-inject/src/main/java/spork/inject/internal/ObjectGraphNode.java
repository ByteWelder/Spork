package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Scope;

import spork.exceptions.BindContext;
import spork.exceptions.BindContextBuilder;
import spork.inject.internal.lang.Annotations;
import spork.internal.Reflection;

/**
 * A node represents an injection point (@Provides method) in a module.
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
			return Reflection.invokeMethod(method, parent, arguments);
		} catch (InvocationTargetException e) {
			BindContext bindContext = new BindContextBuilder(Inject.class)
					.bindingFrom(method)
					.bindingInto(injectSignature.toString())
					.build();

			String message = "Failed to invoke " + method.getDeclaringClass().getName() + "." + method.getName() + "(): " + e.getMessage();

			throw new ObjectGraphException(message, e, bindContext);
		}
	}

	@Nullable
	Object[] collectParameters(ObjectGraphImpl objectGraph) throws ObjectGraphException {
		try {
			return objectGraph.getInjectableMethodParameters(method);
		} catch (ObjectGraphException e) {
			BindContext bindContext = new BindContextBuilder(Inject.class)
					.bindingFrom(method)
					.bindingInto(injectSignature.toString())
					.build();

			String message = "Failed to invoke " + method.getDeclaringClass().getName() + "." + method.getName() + "(): " + e.getMessage();

			throw new ObjectGraphException(message, e, bindContext);
		}
	}
}
