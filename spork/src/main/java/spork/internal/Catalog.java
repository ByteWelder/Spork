package spork.internal;


import java.util.ArrayList;
import java.util.List;

import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;

/**
 * The Catalog holds a reference to all known field/method/type binder instances.
 */
public class Catalog {
	private final List<FieldBinder> fieldBinders = new ArrayList<>();
	private final List<MethodBinder> methodBinders = new ArrayList<>();
	private final List<TypeBinder> typeBinders = new ArrayList<>();

	public void add(FieldBinder<?> fieldBinder) {
		fieldBinders.add(fieldBinder);
	}

	public void add(MethodBinder<?> methodBinder) {
		methodBinders.add(methodBinder);
	}

	public void add(TypeBinder<?> typeBinder) {
		typeBinders.add(typeBinder);
	}

	/**
	 * @return all registered FieldBinder instances
	 */
	List<FieldBinder> getFieldBinders() {
		return fieldBinders;
	}

	/**
	 * @return all registered MethodBinder instances
	 */
	List<MethodBinder> getMethodBinders() {
		return methodBinders;
	}

	/**
	 * @return all registered TypeBinder instances
	 */
	List<TypeBinder> getTypeBinders() {
		return typeBinders;
	}
}