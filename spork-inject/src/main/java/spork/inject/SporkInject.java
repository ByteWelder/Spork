package spork.inject;

import javax.inject.Named;
import javax.inject.Singleton;

import spork.Spork;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.inject.internal.annotationserializers.NamedSerializer;
import spork.inject.internal.annotationserializers.SingletonSerializer;
import spork.BinderRegistry;
import spork.SporkExtension;

public class SporkInject implements SporkExtension {

	static {
		AnnotationSerializers.register(Named.class, new NamedSerializer());
		AnnotationSerializers.register(Singleton.class, new SingletonSerializer());
	}

	@Override
	public void initialize(Spork spork) {
		BinderRegistry binderRegistry = spork.getBinderRegistry();
		binderRegistry.register(new InjectFieldBinder());
		binderRegistry.register(new InjectMethodBinder());
	}
}
