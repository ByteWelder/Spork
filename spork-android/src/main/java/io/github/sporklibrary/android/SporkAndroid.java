package io.github.sporklibrary.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.sporklibrary.BinderRegistry;
import io.github.sporklibrary.android.interfaces.ContextResolver;
import io.github.sporklibrary.android.interfaces.FragmentResolver;
import io.github.sporklibrary.android.interfaces.ViewResolver;
import io.github.sporklibrary.android.internal.ContextResolverManager;
import io.github.sporklibrary.android.internal.DefaultContextResolver;
import io.github.sporklibrary.android.internal.DefaultFragmentResolver;
import io.github.sporklibrary.android.internal.DefaultViewResolver;
import io.github.sporklibrary.android.internal.FragmentResolverManager;
import io.github.sporklibrary.android.internal.ResolverRegistry;
import io.github.sporklibrary.android.internal.ViewResolverManager;
import io.github.sporklibrary.android.internal.binders.BindClickBinder;
import io.github.sporklibrary.android.internal.binders.BindFragmentBinder;
import io.github.sporklibrary.android.internal.binders.BindLayoutBinder;
import io.github.sporklibrary.android.internal.binders.BindResourceBinder;
import io.github.sporklibrary.android.internal.binders.BindViewBinder;

public final class SporkAndroid {
	private static final FragmentResolverManager fragmentResolverManager = new FragmentResolverManager();
	private static final ViewResolverManager viewResolverManager = new ViewResolverManager();
	private static final ContextResolverManager contextResolverManager = new ContextResolverManager();

	static {
		fragmentResolverManager.register(new DefaultFragmentResolver());
		viewResolverManager.register(new DefaultViewResolver());
		contextResolverManager.register(new DefaultContextResolver());
	}

	/**
	 * Register all binders to a specific BinderRegistry.
	 * Warning: Do not call this manually. This is called automatically by the Spork core libraries.
	 *
	 * @param binderRegistry the binder manager to register to
	 */
	public static void initialize(BinderRegistry binderRegistry) {
		binderRegistry.register(new BindLayoutBinder()); // layouts must be bound before views
		binderRegistry.register(new BindViewBinder(viewResolverManager));
		binderRegistry.register(new BindFragmentBinder(fragmentResolverManager));
		binderRegistry.register(new BindClickBinder(viewResolverManager));
		binderRegistry.register(new BindResourceBinder(contextResolverManager));

		tryInitializeSupportBindings();
	}

	/**
	 * @return the ResolverRegistry to register new FragmentResolver instances
	 */
	public ResolverRegistry<FragmentResolver> getFragmentResolverRegistry() {
		return fragmentResolverManager;
	}

	/**
	 * @return the ResolverRegistry to register new ViewResolver instances
	 */
	public ResolverRegistry<ViewResolver> getViewResolverRegistry() {
		return viewResolverManager;
	}

	/**
	 * @return the ResolverRegistry to register new ContextResolver instances
	 */
	public ResolverRegistry<ContextResolver> getContextResolverRegistry() {
		return contextResolverManager;
	}

	/**
	 * Try to smartly initialize spork-android-support through reflection.
	 */
	private static void tryInitializeSupportBindings() {
		try {
			Class<?> e = Class.forName("io.github.sporklibrary.android.support.SporkAndroidSupport");
			Method initializeMethod = e.getDeclaredMethod("initialize");
			initializeMethod.invoke(null);
			System.out.println("Spork for Android initialized with Support library bindings");
		} catch (ClassNotFoundException var3) {
			System.out.println("Spork for Android initialized without Support library bindings");
		} catch (NoSuchMethodException var4) {
			System.out.println("Spork Android: Spork Android Support library found, but initialize method is not present");
		} catch (InvocationTargetException var5) {
			System.out.println("Spork Android: Spork Android Support library found, but initialization failed because of InvocationTargetException: " + var5.getMessage());
		} catch (IllegalAccessException var6) {
			System.out.println("Spork Android: Spork Android Support library found, but initialization failed because of IllegalAccessException: " + var6.getMessage());
		}
	}
}
