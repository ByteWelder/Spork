package spork.injection;

import spork.Spork;
import spork.injection.internal.InjectFieldBinder;
import spork.interfaces.SporkExtension;

public class SporkInjection implements SporkExtension {
	@Override
	public void initialize(Spork spork) {
		spork.getBinderRegistry().register(new InjectFieldBinder());
	}
}
