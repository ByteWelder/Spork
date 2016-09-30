package io.github.sporklibrary.internal;

import java.util.List;

import io.github.sporklibrary.interfaces.Binder;
import io.github.sporklibrary.interfaces.BinderRegistry;

/**
 * Holds the ClassBinderCache for all classes.
 */
public interface BinderCache extends BinderRegistry {
	/**
	 * Gets the ClassBinderCache for a given type.
	 * If the ClassBinderCache isn't available yet, it will be created and returned.
	 *
	 * @param type the class for the cached type
	 * @return the cache
	 */
	List<Binder> getBinders(Class<?> type);
}
