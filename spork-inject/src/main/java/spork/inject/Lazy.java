package spork.inject;

/**
 * A handle to a lazily-computed object instance.
 * Each instance of Lazy computes its own object instance on the first call of get() and
 * caches that value for any subsequent calls to get().
 */
public interface Lazy<T> {
	T get();
}