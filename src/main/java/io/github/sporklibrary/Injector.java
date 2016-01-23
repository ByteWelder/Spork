package io.github.sporklibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The Injector manages InjectionProviders to inject an object for all annotated fields.
 * By default, the InjectionProviderImpl is added to an Injector.
 */
public class Injector
{
	private final List<InjectionProvider> mInjectionProviders = new ArrayList<>(1);

	// Each injection provider has its own field retriever for smart caching
	private final List<AnnotationFieldRetriever> mAnnotationFieldRetrievers = new ArrayList<>(1);

	public Injector()
	{
		// Default injection provider for Inject.class
		addInjectionProvider(new InjectionProviderImpl());
	}

	/**
	 * Add a new injection provider to the Injector
	 * @param injectionProvider the injection provider
	 */
	public void addInjectionProvider(InjectionProvider injectionProvider)
	{
		mInjectionProviders.add(injectionProvider);
		AnnotationFieldRetriever<?> field_retriever = new AnnotationFieldRetriever<>(injectionProvider.getAnnotationClass());
		mAnnotationFieldRetrievers.add(field_retriever);
	}

	/**
	 * Inject all annotations or the provided object.
	 * @param object the object to inject objects into
	 */
	public void inject(Object object)
	{
		// Inject for all InjectionProviders
		for (int i = 0; i < mInjectionProviders.size(); ++i)
		{
			inject(object, mInjectionProviders.get(i), mAnnotationFieldRetrievers.get(i));
		}
	}

	private void inject(Object object, InjectionProvider injectionProvider, AnnotationFieldRetriever<?> annotationFieldRetriever)
	{
		Set<AnnotatedField> annotated_field_set = annotationFieldRetriever.getAnnotatedFields(object.getClass());

		// Inject all fields for this object
		for (AnnotatedField annotated_field : annotated_field_set)
		{
			injectionProvider.inject(object, annotated_field);
		}
	}
}