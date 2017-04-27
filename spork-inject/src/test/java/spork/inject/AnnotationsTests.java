package spork.inject;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Scope;
import javax.inject.Singleton;

import spork.inject.internal.lang.Annotations;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static spork.matchers.Matchers.annotationOfType;

public class AnnotationsTests {
	private static class Testable {
		@Singleton
		Object scopedField;

		@Singleton
		void scopedMethod() {
		}

		void scopedMethodArguments(@Singleton Object object) {
		}

		void scopedMethodArgumentsMissing(Object object) {
		}
	}

	@Test
	public void fieldScopeTest() throws NoSuchFieldException {
		Field field = Testable.class.getDeclaredField("scopedField");
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, field);

		assertThat(namedAnnotation, allOf(notNullValue(), annotationOfType(Singleton.class)));
	}

	@Test
	public void methodScopeTest() throws NoSuchMethodException {
		Method method = Testable.class.getDeclaredMethod("scopedMethod");
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, method);

		assertThat(namedAnnotation, allOf(notNullValue(), annotationOfType(Singleton.class)));
	}

	@Test
	public void methodArgumentScopeTest() throws NoSuchMethodException {
		Method method = Testable.class.getDeclaredMethod("scopedMethodArguments", Object.class);
		Annotation[] annotations = method.getParameterAnnotations()[0];
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, annotations);

		assertThat(namedAnnotation, allOf(notNullValue(), annotationOfType(Singleton.class)));
	}

	@Test
	public void methodArgumentScopeMissingTest() throws NoSuchMethodException {
		Method method = Testable.class.getDeclaredMethod("scopedMethodArgumentsMissing", Object.class);
		Annotation[] annotations = method.getParameterAnnotations()[0];
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, annotations);

		assertThat(namedAnnotation, is(nullValue()));
	}
}
