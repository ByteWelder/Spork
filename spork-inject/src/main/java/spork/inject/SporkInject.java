package spork.inject;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.interfaces.BinderRegistry;
import spork.interfaces.SporkExtension;

public class SporkInject implements SporkExtension {

	@Override
	public void initialize(Spork spork) {
		BinderRegistry binderRegistry = spork.getBinderRegistry();
		binderRegistry.register(new InjectFieldBinder());
		binderRegistry.register(new InjectMethodBinder());
	}
}
