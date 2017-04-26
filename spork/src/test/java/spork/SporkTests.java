package spork;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import spork.stubs.BindFieldTarget;
import spork.stubs.BindMethodTarget;
import spork.stubs.BindTypeTarget;
import spork.stubs.TestAnnotation;
import spork.stubs.TestFieldBinder;
import spork.stubs.TestMethodBinder;
import spork.stubs.TestTypeBinder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SporkTests {
	private static TestFieldBinder fieldBinder;
	private static TestMethodBinder methodBinder;
	private static TestTypeBinder typeBinder;

	@BeforeClass
	public static void setup() {
		// Binders need to be registered once before the first bind().
		// The test binders will stay alive for the duration of all other tests, but that's not
		// a problem because this class is the only one that is supposed to test the shared instance.

		fieldBinder = spy(new TestFieldBinder());
		Spork.register(fieldBinder);

		methodBinder = spy(new TestMethodBinder());
		Spork.register(methodBinder);

		typeBinder = spy(new TestTypeBinder());
		Spork.register(typeBinder);
	}

	@Test
	public void bindField() {
		BindFieldTarget toBind = new BindFieldTarget();
		Spork.bind(toBind);

		verify(fieldBinder).bind(eq(toBind), any(TestAnnotation.class), any(Field.class), any(Object[].class));
	}

	@Test
	public void bindMethod() {
		BindMethodTarget toBind = new BindMethodTarget();
		Spork.bind(toBind);

		verify(methodBinder).bind(eq(toBind), any(TestAnnotation.class), any(Method.class), any(Object[].class));
	}

	@Test
	public void bindType() {
		BindTypeTarget toBind = new BindTypeTarget();
		Spork.bind(toBind);

		verify(typeBinder).bind(eq(toBind), any(TestAnnotation.class), any(Class.class), any(Object[].class));
	}
}
