package spork.inject.internal;

import org.junit.Test;

import java.lang.annotation.Annotation;

import javax.inject.Named;
import javax.inject.Qualifier;

import spork.inject.AnnotationSerializerRegistry;

public class AnnotationSerializerRegistryTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testUnregistered() {
		Annotation annotation = unregisteredAnnotation();
		AnnotationSerializerRegistry.serialize(annotation);
	}

	private static Annotation unregisteredAnnotation() {
		return Named.class.getAnnotation(Qualifier.class);
	}
}
