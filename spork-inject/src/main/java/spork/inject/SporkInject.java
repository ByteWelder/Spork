package spork.inject;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.ModuleManager;
import spork.inject.internal.ModuleMethodRetriever;
import spork.inject.internal.SingletonCache;
import spork.interfaces.SporkExtension;

public class SporkInject implements SporkExtension {
	@Override
	public void initialize(Spork spork) {
		ModuleMethodRetriever methodRetriever = new ModuleMethodRetriever();
		SingletonCache singletonCache = new SingletonCache();
		ModuleManager moduleManager = new ModuleManager(methodRetriever, singletonCache);
		InjectFieldBinder injectFieldBinder = new InjectFieldBinder(moduleManager);
		spork.getBinderRegistry().register(injectFieldBinder);
	}
}
