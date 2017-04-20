package spork.inject;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.SporkExtension;

public class SporkInject implements SporkExtension {

	@Override
	public void initialize(Spork spork) {
		spork.register(new InjectFieldBinder());
		spork.register(new InjectMethodBinder());
	}
}
