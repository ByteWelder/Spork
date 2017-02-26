package spork.benchmark.binders;

import java.lang.reflect.Field;

import spork.interfaces.FieldBinder;

public class FieldBinder2 implements FieldBinder<BindTest2> {
	private static int bindCount = 0;

	@Override
	public void bind(Object object, BindTest2 annotation, Field field, Object[] parameters) {
		bindCount++;
	}

	@Override
	public Class<BindTest2> getAnnotationClass() {
		return BindTest2.class;
	}

	public static int getBindCount() {
		return bindCount;
	}
}