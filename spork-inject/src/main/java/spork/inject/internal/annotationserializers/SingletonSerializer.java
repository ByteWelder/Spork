package spork.inject.internal.annotationserializers;

import javax.inject.Singleton;

import spork.inject.AnnotationSerializer;

public class SingletonSerializer implements AnnotationSerializer<Singleton> {

	@Override
	public String serialize(Singleton annotation) {
		return "Singleton";
	}
}
