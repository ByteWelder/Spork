package spork.inject;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.interfaces.SporkExtension;

public class SporkInject implements SporkExtension {
	@Override
	public void initialize(Spork spork) {
		InjectFieldBinder injectFieldBinder = new InjectFieldBinder();
		spork.getBinderRegistry().register(injectFieldBinder);
	}
}
