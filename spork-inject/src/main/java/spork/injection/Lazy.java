package spork.injection;

/**
 * A handle to a lazily-computed object instance (referred to as "property").
 * Each {@code Lazy} computes its property when the first call to {@code get()} is made and remembers this for all consecutive calls to {@code get()}.
 * @param <T> the property type
 */
public interface Lazy<T> {
	/**
	 * @return the underlying property, computing the property if necessary. All calls to the same {@code Lazy} instance will return the same result.
	 */
	T get();
}
