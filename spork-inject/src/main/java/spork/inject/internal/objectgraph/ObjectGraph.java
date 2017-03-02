package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.Spork;
import spork.inject.internal.InjectSignature;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;
import spork.inject.internal.objectgraph.providers.InstanceCacheProvider;
import spork.inject.internal.objectgraph.providers.InstanceProvider;

public final class ObjectGraph {
	@Nullable
	private final ObjectGraph parentGraph;
	private final ObjectGraphNode[] nodes;
	private final InstanceCache instanceCache = new InstanceCache();

	ObjectGraph(@Nullable ObjectGraph parentGraph, ObjectGraphNode[] nodes) {
		this.parentGraph = parentGraph;
		this.nodes = nodes;
	}

	@Nullable
	public <T> Provider<T> findProvider(Field targetField, Class<T> targetType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, targetField);
		Nullability nullability = Nullability.create(targetField);
		InjectSignature injectSignature = new InjectSignature(targetType, nullability, qualifierAnnotation);

		return findProvider(injectSignature);
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public <T> Provider<T> findProvider(InjectSignature injectSignature) {
		ObjectGraphNode node = findNode(injectSignature);

		if (node == null) {
			return null;
		}

		Object[] parameters = node.collectParameters(this);

		// No scope and no qualifier means a new instance per injection
		if (node.getScopeId() == null && !injectSignature.hasQualifier()) {
			return (Provider<T>) new InstanceProvider(node, parameters);
		} else {
			return (Provider<T>) new InstanceCacheProvider(node, parameters, instanceCache);
		}

		// else convert to Provider
	}

	@Nullable
	public Object[] collectParameters(Class<?>[] parameterTypes, Annotation[][] parameterAnnotations, Type[] genericParameterTypes) throws ObjectGraphException {
		int parameterCount = parameterTypes.length;
		if (parameterCount == 0) {
			return null;
		}

		Object[] argumentInstances = new Object[parameterCount];

		for (int i = 0; i < parameterCount; ++i) {
			// fetch all relevant argument data
			Class<?> parameterClass = parameterTypes[i];
			Annotation[] annotations = parameterAnnotations[i];
			Type genericParameterType = genericParameterTypes[i];

			// retrieve provider
			InjectSignature injectSignature = getInjectSignature(parameterClass, annotations, genericParameterType);
			Provider provider = findProvider(injectSignature);

			if (provider == null) {
				throw new ObjectGraphException("invocation argument not found: " + injectSignature.toString()); // TODO: use different exception
			}

			boolean parameterIsProvider = (parameterClass == Provider.class);

			// store provider or instance
			argumentInstances[i] = parameterIsProvider ? provider : provider.get();
		}

		return argumentInstances;
	}

	private static InjectSignature getInjectSignature(Class<?> parameterClass, Annotation[] annotations, Type genericParameterType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, annotations);
		Nullability nullability = Nullability.create(annotations);
		boolean parameterIsProvider = (parameterClass == Provider.class);
		// Determine the true type of the instance (so not Provider.class)
		Class<?> targetType = parameterIsProvider ? (Class<?>) ((ParameterizedType) genericParameterType).getActualTypeArguments()[0] : parameterClass;
		return new InjectSignature(targetType, nullability, qualifierAnnotation);
	}

	@Nullable
	private ObjectGraphNode findNode(InjectSignature injectSignature) {
		for (ObjectGraphNode node : nodes) {
			if (node.getInjectSignature().equals(injectSignature)) {
				return node;
			}
		}

		if (parentGraph != null) {
			return parentGraph.findNode(injectSignature);
		} else {
			return null;
		}
	}

	/**
	 * A shortcut to Spork.bind(object, objectGraph).
	 * This binds all known annotations for the shared Spork instance including spork-inject.
	 * @param object the object to bind
	 */
	public void inject(Object object) {
		Spork.bind(object, this);
	}

	/**
	 * A shortcut to spork.getBinder().bind(object, objectGraph)
	 * This binds all known annotations for the given Spork instance including spork-inject.
	 * @param object the object to bind
	 */
	public void inject(Object object, Spork spork) {
		spork.getBinder().bind(object, this);
	}
}
