package spork.android;

import spork.SporkInstance;
import spork.SporkExtension;
import spork.android.extension.ContextResolver;
import spork.android.extension.FragmentResolver;
import spork.android.extension.ViewResolver;
import spork.android.internal.CompoundContextResolver;
import spork.android.internal.DefaultContextResolver;
import spork.android.internal.DefaultFragmentResolver;
import spork.android.internal.DefaultViewResolver;
import spork.android.internal.CompoundFragmentResolver;
import spork.android.internal.CompoundViewResolver;
import spork.android.internal.SporkAndroidExtensionLoader;
import spork.android.internal.binders.BindClickBinder;
import spork.android.internal.binders.BindFragmentBinder;
import spork.android.internal.binders.BindLayoutBinder;
import spork.android.internal.binders.BindResourceBinder;
import spork.android.internal.binders.BindViewBinder;

/**
 * Extension that adds binders that provide Android-specific features.
 * This class is automatically instantiated and initialized by the static Spork class.
 *
 * If you create your own SporkInstance, you need to register an extension manually.
 */
public final class SporkAndroid implements SporkExtension {
	private final CompoundFragmentResolver compoundFragmentResolver = new CompoundFragmentResolver();
	private final CompoundViewResolver compoundViewResolver = new CompoundViewResolver();
	private final CompoundContextResolver compoundContextResolver = new CompoundContextResolver();

	public SporkAndroid() {
		compoundFragmentResolver.add(new DefaultFragmentResolver());
		compoundViewResolver.add(new DefaultViewResolver());
		compoundContextResolver.add(new DefaultContextResolver());
	}

	@Override
	public void initialize(SporkInstance spork) {
		spork.register(new BindLayoutBinder()); // layouts must be bound before views
		spork.register(new BindViewBinder(compoundViewResolver));
		spork.register(new BindFragmentBinder(compoundFragmentResolver));
		spork.register(new BindClickBinder(compoundViewResolver));
		spork.register(new BindResourceBinder(compoundContextResolver));

		SporkAndroidExtensionLoader.load(this, "spork.android.support.SporkAndroidSupport");
	}

	/**
	 * Extension registration method to add a custom FragmentResolver.
	 */
	public void register(FragmentResolver fragmentResolver) {
		compoundFragmentResolver.add(fragmentResolver);
	}

	/**
	 * Extension registration method to add a custom ViewResolver.
	 */
	public void register(ViewResolver viewResolver) {
		compoundViewResolver.add(viewResolver);
	}

	/**
	 * Extension registration method to add a custom ContextResolver.
	 */
	public void register(ContextResolver contextResolver) {
		compoundContextResolver.add(contextResolver);
	}
}
