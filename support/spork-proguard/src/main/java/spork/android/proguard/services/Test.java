package spork.android.proguard.services;

import javax.inject.Inject;

import spork.android.proguard.ApplicationModule;
import spork.inject.internal.ObjectGraphBuilder;

public class Test {

	@Inject
	private RestService restService;

	public Test() {
		new ObjectGraphBuilder()
				.module(new ApplicationModule())
				.build()
				.inject(this);

		restService.doSomething();
	}
}
