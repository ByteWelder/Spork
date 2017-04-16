package spork.inject;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.BinderRegistry;
import spork.SporkExtension;

public class SporkInject implements SporkExtension {

	@Override
	public void initialize(Spork spork) {
		BinderRegistry binderRegistry = spork.getBinderRegistry();
		binderRegistry.register(new InjectFieldBinder());
		binderRegistry.register(new InjectMethodBinder());
	}
}
