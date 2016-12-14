package spork.inject;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.ModuleManager;
import spork.interfaces.SporkExtension;

public class SporkInject implements SporkExtension {
	@Override
	public void initialize(Spork spork) {
		ModuleManager moduleManager = new ModuleManager();
		InjectFieldBinder injectFieldBinder = new InjectFieldBinder(moduleManager);
		spork.getBinderRegistry().register(injectFieldBinder);
	}
}
