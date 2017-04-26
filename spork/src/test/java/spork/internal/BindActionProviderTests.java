package spork.internal;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import spork.stubs.BindAllTarget;
import spork.stubs.BindFieldTarget;
import spork.stubs.BindMethodTarget;
import spork.stubs.BindTypeTarget;
import spork.stubs.TestFieldBinder;
import spork.stubs.TestMethodBinder;
import spork.stubs.TestTypeBinder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;

public class BindActionProviderTests {
	private Catalog catalog;

	@Before
	public void setup() {
		catalog = spy(new Catalog());
		catalog.add(new TestFieldBinder());
		catalog.add(new TestMethodBinder());
		catalog.add(new TestTypeBinder());
	}

	@Test
	public void fieldAction() {
		BindActionProvider actionProvider = new BindActionProvider(catalog);
		List<BindAction> bindActions = actionProvider.getBindActions(BindFieldTarget.class);

		assertThat(bindActions.size(), is(1));
	}

	@Test
	public void methodAction() {
		BindActionProvider actionProvider = new BindActionProvider(catalog);
		List<BindAction> bindActions = actionProvider.getBindActions(BindMethodTarget.class);

		assertThat(bindActions.size(), is(1));
	}

	@Test
	public void typeAction() {
		BindActionProvider actionProvider = new BindActionProvider(catalog);
		List<BindAction> bindActions = actionProvider.getBindActions(BindTypeTarget.class);

		assertThat(bindActions.size(), is(1));
	}

	@Test
	public void compoundActions() {
		BindActionProvider actionProvider = new BindActionProvider(catalog);
		List<BindAction> bindActions = actionProvider.getBindActions(BindAllTarget.class);

		assertThat(bindActions.size(), is(3));
	}
}
