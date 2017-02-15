package spork.inject;

import org.junit.Test;

import javax.annotation.Nullable;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.modules.StringNullModule;

import static org.junit.Assert.assertNull;

public class InjectNullableTests {
	private static class NullableParent {
		@Inject
		@Nullable
		String string;
	}

	private static class BadParent {
		@Inject
		String string;
	}


}
