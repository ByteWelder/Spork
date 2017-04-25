package spork.stubs;

import java.lang.reflect.Method;

import spork.extension.MethodBinder;

public class TestMethodBinder implements MethodBinder<TestAnnotation> {

	@Override
	public void bind(Object object, TestAnnotation annotation, Method method, Object... parameters) {
	}

	@Override
	public Class<TestAnnotation> getAnnotationClass() {
		return TestAnnotation.class;
	}
}