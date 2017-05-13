package spork.benchmark.core;

import java.lang.reflect.Field;

import spork.extension.FieldBinder;

final class TestBinder implements FieldBinder<TestAnnotation> {
	private long bindCount = 0;

	@Override
	public void bind(Object object, TestAnnotation annotation, Field field, Object... parameters) {
		bindCount++;
	}

	@Override
	public Class<TestAnnotation> getAnnotationClass() {
		return TestAnnotation.class;
	}
}
