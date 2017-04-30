package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

/**
 * Reflection utilities that throw BindFailed exceptions with contextual information when something fails.
 */
public final class Reflection {

	private Reflection() {
	}

	/**
	 * Set a value for a Field on an object
	 *
	 * @param annotationClass   class of the annotation that was relevant to this invocation (used for Exception throwing info)
	 * @param field             the Field to set the value for
	 * @param parentObject      the parent object that holds the Field
	 * @param valueObject       the field value to bind
	 */
	@SuppressWarnings({ "PMD.EmptyCatchBlock", "PMD.PreserveStackTrace" })
	public static void setFieldValue(Class<? extends Annotation> annotationClass, Field field, Object parentObject, Object valueObject) {
		try {
			field.setAccessible(true);
			field.set(parentObject, valueObject);
		} catch (IllegalAccessException e) {
			throw bindFailedBuilder(annotationClass, "field is not accessible")
					.from(valueObject.getClass())
					.into(field)
					.cause(e)
					.build();
		} finally {
			field.setAccessible(false);
		}
	}

	/**
	 * Invoke a Method
	 *
	 * @param annotationClass   class of the annotation that was relevant to this invocation (used for Exception throwing info)
	 * @param method            the method to invoke on the specified instance
	 * @param object            the parent object to invoke the method on
	 * @param args              the method invocation arguments
	 * @return the result of the invoked method
	 */
	// remove PMD.AvoidCatchingGenericException when minSdk is Android 4.4+
	@SuppressWarnings({ "PMD.EmptyCatchBlock", "PMD.PreserveStackTrace", "PMD.AvoidCatchingGenericException" })
	@Nullable
	public static Object invokeMethod(Class<? extends Annotation> annotationClass, Method method, Object object, Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch (Exception e) { // TODO: multi-catch IllegalAccessException, InvocationTargetException when supporting Android 4.4+
			throw bindFailedBuilder(annotationClass, "failed to invoke method")
					.suggest("The method might not be accessible or threw an Exception. Check the cause below.")
					.into(method)
					.cause(e)
					.build();
		} finally {
			method.setAccessible(false);
		}
	}
}
