package spork.inject.internal.annotationserializers;

import javax.inject.Named;

import spork.inject.AnnotationSerializer;

public class NamedSerializer implements AnnotationSerializer<Named> {
	@Override
	public String serialize(Named annotation) {
		return "Named(" + annotation.value() + ")";
	}
}
