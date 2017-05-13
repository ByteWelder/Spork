package spork.inject;

import spork.SporkInstance;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.SporkExtension;

/**
 * This class is automatically instantiated and initialized by the static Spork class.
 *
 * If you create your own SporkInstance, you need to register an extension manually.
 */
public final class SporkInject implements SporkExtension {

	@Override
	public void initialize(SporkInstance spork) {
		spork.register(new InjectFieldBinder());
		spork.register(new InjectMethodBinder());
	}
}
