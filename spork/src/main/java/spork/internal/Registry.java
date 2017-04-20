package spork.internal;


import java.util.ArrayList;
import java.util.List;

import spork.FieldBinder;
import spork.MethodBinder;
import spork.TypeBinder;

/**
 * Keeps track of all annotation binder implementations.
 */
public class Registry {
	private final List<FieldBinder<?>> fieldBinders = new ArrayList<>();
	private final List<MethodBinder<?>> methodBinders = new ArrayList<>();
	private final List<TypeBinder<?>> typeBinders = new ArrayList<>();

	public void register(FieldBinder<?> fieldBinder) {
		fieldBinders.add(fieldBinder);
	}

	public void register(MethodBinder<?> methodBinder) {
		methodBinders.add(methodBinder);
	}

	public void register(TypeBinder<?> typeBinder) {
		typeBinders.add(typeBinder);
	}

	/**
	 * @return all registered FieldBinder instances
	 */
	List<FieldBinder<?>> getFieldBinders() {
		return fieldBinders;
	}

	/**
	 * @return all registered MethodBinder instances
	 */
	List<MethodBinder<?>> getMethodBinders() {
		return methodBinders;
	}

	/**
	 * @return all registered TypeBinder instances
	 */
	List<TypeBinder<?>> getTypeBinders() {
		return typeBinders;
	}
}