package spork.test.methodbinding;

import java.lang.reflect.Method;

import spork.interfaces.MethodBinder;

public class BindMethodBinder implements MethodBinder<BindMethod> {
	private int methodCount = 0;

	@Override
	public void bind(Object instance, BindMethod annotation, Method method, Object[] modules) {
		methodCount++;
	}

	@Override
	public Class<BindMethod> getAnnotationClass() {
		return BindMethod.class;
	}

	public int getMethodCount() {
		return methodCount;
	}
}
