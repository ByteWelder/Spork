package spork.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import spork.exceptions.UnexpectedException;

/**
 * Reflection utilities
 */
public final class Reflection {

	private Reflection() {
	}

	/**
	 * Set a value for a Field on an object
	 *
	 * @param field             the Field to set the value for
	 * @param parentObject      the parent object that holds the Field
	 * @param valueObject       the field value to bind
	 */
	public static void setFieldValue(Field field, Object parentObject, Object valueObject) {
		try {
			field.setAccessible(true);
			field.set(parentObject, valueObject);
		} catch (IllegalAccessException e) {
			throw new UnexpectedException("Failed to access " + field.getDeclaringClass().getName() + "." + field.getName()
					+ "\nThere might be a concurrency problem or you are trying to access a final static Field.", e);
		} finally {
			field.setAccessible(false);
		}
	}

	/**
	 * Invoke a Method
	 *
	 * @param method            the method to invoke on the specified instance
	 * @param object            the parent object to invoke the method on
	 * @param args              the method invocation arguments
	 * @return the result of the invoked method
	 */
	@Nullable
	public static Object invokeMethod(Method method, Object object, Object... args) throws InvocationTargetException {
		try {
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch (IllegalAccessException e) {
			throw new UnexpectedException("Failed to access a Method that was previously made accessible. Maybe there is a concurrency problem?", e);
		} finally {
			method.setAccessible(false);
		}
	}
}
