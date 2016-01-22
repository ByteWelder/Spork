package net.kenvanhoeylandt.spork.injectors;

import net.kenvanhoeylandt.spork.InjectField;
import net.kenvanhoeylandt.spork.Injector;
import net.kenvanhoeylandt.spork.InstanceManager;
import net.kenvanhoeylandt.spork.annotations.retrieval.InjectRetriever;

import java.lang.reflect.Field;
import java.util.Set;

public class DefaultInjector implements Injector
{
	private final InjectRetriever mInjectRetriever = new InjectRetriever();

	private final InstanceManager mInstanceManager = new InstanceManager();

	@Override
	public void inject(Object object)
	{
		Set<InjectField> inject_field_set = mInjectRetriever.getInjectFields(object.getClass());

		for (InjectField inject_field : inject_field_set)
		{
			Object instance = mInstanceManager.getInstance(inject_field, object);

			try
			{
				Field field = inject_field.getField();

				boolean field_accessible = field.isAccessible();
				field.setAccessible(true);
				field.set(object, instance);
				field.setAccessible(field_accessible);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
