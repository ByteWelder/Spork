package spork.inject;

import spork.SporkInstance;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.SporkExtension;

public class SporkInject implements SporkExtension {

	@Override
	public void initialize(SporkInstance spork) {
		spork.register(new InjectFieldBinder());
		spork.register(new InjectMethodBinder());
	}
}
