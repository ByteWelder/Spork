package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * A model that contains an Annotation and a Field
 * @param <AnnotationType> the annotation type to store
 */
public class AnnotatedField<AnnotationType extends Annotation>
{
	private final AnnotationType mAnnotation;

	private final Field mField;

	public AnnotatedField(AnnotationType annotation, Field field)
	{
		mAnnotation = annotation;
		mField = field;
	}

	public AnnotationType getAnnotation()
	{
		return mAnnotation;
	}

	public Field getField()
	{
		return mField;
	}
}
