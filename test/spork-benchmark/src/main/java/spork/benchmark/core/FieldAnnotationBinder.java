package spork.benchmark.core;

import java.lang.reflect.Field;

import spork.interfaces.FieldBinder;

public class FieldAnnotationBinder implements FieldBinder<FieldAnnotation> {
	private static long bindCount = 0;

	@Override
	public void bind(Object object, FieldAnnotation annotation, Field field, Object[] parameters) {
		bindCount++;
	}

	@Override
	public Class<FieldAnnotation> getAnnotationClass() {
		return FieldAnnotation.class;
	}
}
