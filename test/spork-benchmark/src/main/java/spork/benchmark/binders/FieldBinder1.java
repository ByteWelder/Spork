package spork.benchmark.binders;

import java.lang.reflect.Field;

import spork.interfaces.FieldBinder;

public class FieldBinder1 implements FieldBinder<BindTest1> {
	private static long bindCount = 0;

	public static void setBindCount(long bindCount) {
		FieldBinder1.bindCount = bindCount;
	}

	@Override
	public void bind(Object object, BindTest1 annotation, Field field, Object[] parameters) {
		bindCount++;
	}

	@Override
	public Class<BindTest1> getAnnotationClass() {
		return BindTest1.class;
	}

	public static long getBindCount() {
		return bindCount;
	}
}
