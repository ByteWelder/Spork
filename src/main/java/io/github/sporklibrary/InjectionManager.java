package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * The InjectionManager manages CompoundInjector objects.
 */
public class InjectionManager
{
	private final List<CompoundInjector> mInjectors = new ArrayList<>();

	/**
	 * Register a new FieldInjector
	 * @param fieldInjector the FieldInjector to register
	 */
	public <AnnotationType extends Annotation> void register(FieldInjector<AnnotationType> fieldInjector)
	{
		mInjectors.add(new CompoundInjector<>(fieldInjector));
	}

	/**
	 * Inject all annotations or the provided object.
	 * @param object the object to inject into
	 */
	public void inject(Object object)
	{
		for (CompoundInjector injector : mInjectors)
		{
			injector.inject(object);
		}
	}
}