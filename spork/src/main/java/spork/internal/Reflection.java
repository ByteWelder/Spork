package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import spork.BindException;

public final class Reflection {

	private Reflection() {
	}

	/**
	 * Set a value for a Field on an object
	 *
	 * @param annotation   the annotation that belongs to the field (used for Exception throwing only)
	 * @param field        the Field to set the value for
	 * @param parentObject the parent object that holds the Field
	 * @param valueObject  the field value to bind
	 */
	@SuppressWarnings("PMD.EmptyCatchBlock")
	public static void setFieldValue(Annotation annotation, Field field, Object parentObject, Object valueObject) {
		try {
			field.setAccessible(true);
			field.set(parentObject, valueObject);
		} catch (IllegalAccessException e) {
			throw new BindException(annotation.getClass(), parentObject.getClass(), field, "field not accessible", e);
		} finally {
			// ensure the Field isn't accessible when it shouldn't be
			try {
				field.setAccessible(false);
			} catch (SecurityException e) {
				// no-op
			}
		}
	}

	/**
	 * Invoke a Method
	 *
	 * @param annotation the annotation that was relevant to this invocation (used for Exception throwing only)
	 * @param method     the method to invoke on the specified instance
	 * @param object     the parent object to invoke the method on
	 * @param args       the method invocation arguments
	 * @return the result of the invoked method
	 */
	@Nullable
	@SuppressWarnings("PMD.EmptyCatchBlock")
	public static Object invokeMethod(Annotation annotation, Method method, Object object, Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch (IllegalAccessException e) {
			throw new BindException(annotation.getClass(), object.getClass(), method, "method not accessible", e);
		} catch (InvocationTargetException e) {
			throw new BindException(annotation.getClass(), object.getClass(), method, "method calling failed because of an invocation issue", e);
		} finally {
			try {
				method.setAccessible(false);
			} catch (SecurityException e) {
				// no-op
			}
		}
	}
}
