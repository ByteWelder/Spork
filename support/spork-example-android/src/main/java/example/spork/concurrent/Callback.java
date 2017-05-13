package example.spork.concurrent;

/**
 * Asynchronous callback interface
 * @param <T> the result type
 */
public interface Callback<T> {
	void onSuccess(T object);
	void onFailure(Exception caught);
}
