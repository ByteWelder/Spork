package net.kenvanhoeylandt.spork.annotations.retrieval;

import net.kenvanhoeylandt.spork.InjectField;
import net.kenvanhoeylandt.spork.annotations.Inject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InjectRetriever
{
	private Map<Class<?>, Set<InjectField>> mClassFieldSetMap = new HashMap<>();

	public Set<InjectField> getInjectFields(Class<?> classObject)
	{
		Set<InjectField> field_set = mClassFieldSetMap.get(classObject);

		if (field_set != null)
		{
			return field_set;
		}

		field_set = new HashSet<>();

		for (Field field : classObject.getDeclaredFields())
		{
			Inject inject = field.getAnnotation(Inject.class);

			if (inject != null)
			{
				field_set.add(new InjectField(inject, field));
			}
		}

		mClassFieldSetMap.put(classObject, field_set);

		return field_set;
	}
}
