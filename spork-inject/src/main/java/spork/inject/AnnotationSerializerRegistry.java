package spork.inject;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Qualifier and scope annotations must resolve to a unique identifier.
 * This class manages the serialization instances for all registered annotations.
 *
 * Examples:
 *  - Qualifier annotations are annotations annotated with {@link javax.inject.Qualifier}
 *    An example of a qualifier annotation is {@link javax.inject.Named}.
 *  - Scope annotations are annotations annotated with {@link javax.inject.Scope}
 *    An example of a qualifier annotation is {@link javax.inject.Singleton}.
 */
public final class AnnotationSerializerRegistry {
	private static final Map<Class<? extends Annotation>, AnnotationSerializer> serializerMap = new HashMap<>(1);

	private AnnotationSerializerRegistry() {
	}

	/**
	 * Register a Qualifier annotation and its serializer.
	 * @param <T> an annotation
	 * @param annotationType .
	 * @param serializer .
	 */
	public static <T extends Annotation> void register(Class<T> annotationType, AnnotationSerializer<T> serializer) {
		serializerMap.put(annotationType, serializer);
	}

	/**
	 * Serialize a qualifier annotation.
	 * @param <T> an annotation
	 * @param annotation the annotation (type) to find a serializer for
	 * @return a serialized annotation
	 * @throws UnsupportedOperationException when a serializer is not found for the given annotation
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> String serialize(T annotation) {
		AnnotationSerializer<T> serializer = (AnnotationSerializer<T>) serializerMap.get(annotation.annotationType());

		if (serializer == null) {
			throw new UnsupportedOperationException("Cannot serialize " + annotation.annotationType().getName() + ". Make sure to register a Serializer through AnnotationSerializerRegistry.register() first.");
		}

		return serializer.serialize(annotation);
	}
}
