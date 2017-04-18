package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Scope;

import spork.inject.internal.lang.Annotations;
import spork.internal.Reflection;

import static spork.BindFailedBuilder.bindFailedBuilder;

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

	public Object resolve(Object... arguments) {
		return Reflection.invokeMethod(Inject.class, method, parent, arguments);
	}

	@Nullable
	@SuppressWarnings("PMD.PreserveStackTrace")
	Object[] collectParameters(ObjectGraph objectGraph) {
		try {
			return ObjectGraphs.getInjectableMethodParameters(objectGraph, method);
		} catch (ObjectGraphException e) {
			String message = "failed to call " + method.getDeclaringClass().getName() + "." + method.getName() + "(): " + e.getMessage();
			throw bindFailedBuilder(Inject.class, message)
					.from(method)
					.into(injectSignature.getType())
					.build();
		}
	}
}
