package io.github.sporklibrary;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.component.ComponentFieldBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Main class to access Spork functionality.
 */
public final class Spork
{
	private static final Logger sLogger = LoggerFactory.getLogger(Spork.class);

	private Spork()
	{
	}

	private static @Nullable BinderManager sBinderManager;

	/**
	 * Bind a single object with all relevant instances.
	 * @param object the object to bind into
	 */
	public static void bind(Object object)
	{
		getBinderManager().bind(object);
	}

	public static BinderManager getBinderManager()
	{
		// Only create an binder if the code is actually used
		if (sBinderManager == null)
		{
			sBinderManager = new BinderManager();
			sBinderManager.register(new ComponentFieldBinder());

			tryInitializeSporkAndroidBindings(sBinderManager);
		}

		return sBinderManager;
	}

	/**
	 * Tries to register the SporkAndroid bindings if the library is present in the classpath.
	 * @param binderManager the binder manager to register bindings to
	 */
	private static void tryInitializeSporkAndroidBindings(BinderManager binderManager)
	{
		try
		{
			Class<?> spork_android_class = Class.forName("io.github.sporklibrary.SporkAndroid");

			Method initialize_method = spork_android_class.getDeclaredMethod("initialize", BinderManager.class);

			initialize_method.invoke(null, binderManager);

			sLogger.debug("BinderManager created with Spork for Android");
		}
		catch (ClassNotFoundException e)
		{
			sLogger.debug("BinderManager created without Spork for Android");
		}
		catch (NoSuchMethodException e)
		{
			sLogger.warn("Spork for Android found, but initialize method is not present");
		}
		catch (InvocationTargetException e)
		{
			sLogger.warn("Spork for Android found, but initialization failed because of InvocationTargetException: " + e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			sLogger.warn("Spork for Android found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		}
	}
}
