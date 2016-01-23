package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * A model that contains an Annotation and a Field
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedField<AnnotationType extends Annotation>
{
	private final AnnotationType mInject;

	private final Field mField;

	public AnnotatedField(AnnotationType inject, Field field)
	{
		mInject = inject;
		mField = field;
	}

	public AnnotationType getAnnotation()
	{
		return mInject;
	}

	public Field getField()
	{
		return mField;
	}
}
