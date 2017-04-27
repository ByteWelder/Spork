package spork.android.proguard.services;

import javax.inject.Inject;

import spork.android.proguard.ApplicationModule;
import spork.inject.ObjectGraphs;

public class Test {

	@Inject
	private RestService restService;

	public Test() {
		ObjectGraphs.builder()
				.module(new ApplicationModule())
				.build()
				.inject(this);

		restService.doSomething();
	}
}
