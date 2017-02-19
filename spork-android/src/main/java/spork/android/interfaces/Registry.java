package spork.android.interfaces;

/**
 * An object that allows you to register objects from a specified type.
 * @param <T> the type to register
 */
public interface Registry<T> {
	void register(T object);
}
