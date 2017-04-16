package spork.internal;


import java.util.ArrayList;
import java.util.List;

import spork.BinderRegistry;
import spork.FieldBinder;
import spork.MethodBinder;
import spork.TypeBinder;

/**
 * Holds a reference of all field, method and type binder instances.
 */
public class BinderManager implements BinderRegistry {
	private final List<FieldBinder<?>> fieldBinders = new ArrayList<>();
	private final List<MethodBinder<?>> methodBinders = new ArrayList<>();
	private final List<TypeBinder<?>> typeBinders = new ArrayList<>();
	private final BinderCache binderCache;

	public BinderManager() {
		this.binderCache = new BinderCache(this);
	}

	@Override
	public void register(FieldBinder<?> fieldBinder) {
		fieldBinders.add(fieldBinder);
		binderCache.register(fieldBinder);
	}

	@Override
	public void register(MethodBinder<?> methodBinder) {
		methodBinders.add(methodBinder);
		binderCache.register(methodBinder);
	}

	@Override
	public void register(TypeBinder<?> typeBinder) {
		typeBinders.add(typeBinder);
		binderCache.register(typeBinder);
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

	public BinderCache getBinderCache() {
		return binderCache;
	}
}