package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import spork.annotations.Nullable;
import spork.exceptions.BindException;

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
	public static void setFieldValue(Annotation annotation, Field field, Object parentObject, Object valueObject) {
		boolean accessible = field.isAccessible();

		try {
			if (accessible) {
				field.set(parentObject, valueObject);
			} else {
				field.setAccessible(true);
				field.set(parentObject, valueObject);
				field.setAccessible(false);
			}
		} catch (IllegalAccessException e) {
			throw new BindException(annotation.getClass(), parentObject.getClass(), field, "field not accessible", e);
		} finally {
			// ensure the Field isn't accessible when it shouldn't be
			if (!accessible && field.isAccessible()) {
				field.setAccessible(false);
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
	public static Object invokeMethod(Annotation annotation, Method method, Object object, Object... args) {
		boolean accessible = method.isAccessible();

		try {
			if (accessible) {
				return method.invoke(object, args);
			} else {
				method.setAccessible(true);
				Object result = method.invoke(object, args);
				method.setAccessible(false);
				return result;
			}
		} catch (IllegalAccessException e) {
			throw new BindException(annotation.getClass(), object.getClass(), method, "method not accessible", e);
		} catch (InvocationTargetException e) {
			throw new BindException(annotation.getClass(), object.getClass(), method, "method calling failed because of an invocation issue", e);
		} finally {
			// ensure the Field isn't accessible when it shouldn't be
			if (!accessible && method.isAccessible()) {
				method.setAccessible(false);
			}
		}
	}
}
