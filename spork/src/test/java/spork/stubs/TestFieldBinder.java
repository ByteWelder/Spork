package spork.stubs;

import java.lang.reflect.Field;

import spork.extension.FieldBinder;

public class TestFieldBinder implements FieldBinder<TestAnnotation> {

	@Override
	public void bind(Object object, TestAnnotation annotation, Field field, Object... parameters) {
	}

	@Override
	public Class<TestAnnotation> getAnnotationClass() {
		return TestAnnotation.class;
	}
}