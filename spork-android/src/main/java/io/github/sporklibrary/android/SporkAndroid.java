package io.github.sporklibrary.android;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.interfaces.ContextResolver;
import io.github.sporklibrary.android.interfaces.FragmentResolver;
import io.github.sporklibrary.android.interfaces.ResolverRegistry;
import io.github.sporklibrary.android.interfaces.ViewResolver;
import io.github.sporklibrary.android.internal.ContextResolverManager;
import io.github.sporklibrary.android.internal.DefaultContextResolver;
import io.github.sporklibrary.android.internal.DefaultFragmentResolver;
import io.github.sporklibrary.android.internal.DefaultViewResolver;
import io.github.sporklibrary.android.internal.FragmentResolverManager;
import io.github.sporklibrary.android.internal.ViewResolverManager;
import io.github.sporklibrary.android.internal.binders.BindClickBinder;
import io.github.sporklibrary.android.internal.binders.BindFragmentBinder;
import io.github.sporklibrary.android.internal.binders.BindLayoutBinder;
import io.github.sporklibrary.android.internal.binders.BindResourceBinder;
import io.github.sporklibrary.android.internal.binders.BindViewBinder;
import io.github.sporklibrary.interfaces.BinderRegistry;

public final class SporkAndroid implements io.github.sporklibrary.SporkExtension {
	private final FragmentResolverManager fragmentResolverManager = new FragmentResolverManager();
	private final ViewResolverManager viewResolverManager = new ViewResolverManager();
	private final ContextResolverManager contextResolverManager = new ContextResolverManager();

	public SporkAndroid() {
		fragmentResolverManager.register(new DefaultFragmentResolver());
		viewResolverManager.register(new DefaultViewResolver());
		contextResolverManager.register(new DefaultContextResolver());
	}

	@Override
	public void initialize(Spork spork) {
		BinderRegistry binderRegistry = spork.getBinderRegistry();

		binderRegistry.register(new BindLayoutBinder()); // layouts must be bound before views
		binderRegistry.register(new BindViewBinder(viewResolverManager));
		binderRegistry.register(new BindFragmentBinder(fragmentResolverManager));
		binderRegistry.register(new BindClickBinder(viewResolverManager));
		binderRegistry.register(new BindResourceBinder(contextResolverManager));

		initializeExtension("io.github.sporklibrary.android.support.SporkAndroidSupport");
	}

	/**
	 * Try to initialize a SporkAndroidExtension.
	 * @param extensionClassName the SporkAndroidExtension class name
	 */
	private void initializeExtension(String extensionClassName) {
		try {
			Class<?> extensionClass = Class.forName(extensionClassName);
			Object extensionObject = extensionClass.newInstance();
			if (extensionObject instanceof SporkAndroidExtension) {
				SporkAndroidExtension extension = (SporkAndroidExtension)extensionObject;
				extension.initialize(this);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			System.out.println("SporkAndroid: extension " + extensionClassName + "found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("SporkAndroid: extension " + extensionClassName + "found, but failed to create instance: " + e.getMessage());
		}
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
}
