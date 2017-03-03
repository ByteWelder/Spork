package spork.inject;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Scope;
import javax.inject.Singleton;

import spork.inject.internal.lang.Annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class InjectScopeTests {
	public static class Testable {
		@Singleton
		public Object scopedField;

		@Singleton
		public void scopedMethod() {
		}

		public void scopedMethodArguments(@Singleton Object object) {
		}

		public void scopedMethodArgumentsMissing(Object object) {
		}
	}

	@Test
	public void fieldScopeTest() throws NoSuchFieldException {
		Field field = Testable.class.getField("scopedField");
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, field);

		assertNotNull(namedAnnotation);
		assertEquals(Singleton.class, namedAnnotation.annotationType());
	}

	@Test
	public void methodScopeTest() throws NoSuchMethodException {
		Method method = Testable.class.getMethod("scopedMethod");
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, method);

		assertNotNull(namedAnnotation);
		assertEquals(Singleton.class, namedAnnotation.annotationType());
	}

	@Test
	public void methodArgumentScopeTest() throws NoSuchMethodException {
		Method method = Testable.class.getMethod("scopedMethodArguments", Object.class);
		Annotation[] annotations = method.getParameterAnnotations()[0];
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, annotations);

		assertNotNull(namedAnnotation);
		assertEquals(Singleton.class, namedAnnotation.annotationType());
	}

	@Test
	public void methodArgumentScopeMissingTest() throws NoSuchMethodException {
		Method method = Testable.class.getMethod("scopedMethodArgumentsMissing", Object.class);
		Annotation[] annotations = method.getParameterAnnotations()[0];
		Annotation namedAnnotation = Annotations.findAnnotationAnnotatedWith(Scope.class, annotations);

		assertNull(namedAnnotation);
	}
}
