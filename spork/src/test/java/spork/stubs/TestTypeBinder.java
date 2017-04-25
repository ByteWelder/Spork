package spork.stubs;

import spork.extension.TypeBinder;

public class TestTypeBinder implements TypeBinder<TestAnnotation> {

	@Override
	public void bind(Object object, TestAnnotation annotation, Class<?> type, Object... parameters) {
	}

	@Override
	public Class<TestAnnotation> getAnnotationClass() {
		return TestAnnotation.class;
	}
}