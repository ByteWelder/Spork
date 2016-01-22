package net.kenvanhoeylandt.spork;

import net.kenvanhoeylandt.spork.annotations.Inject;

import java.lang.reflect.Field;

public class InjectField
{
	private final Inject mInject;

	private final Field mField;

	public InjectField(Inject inject, Field field)
	{
		mInject = inject;
		mField = field;
	}

	public Inject getInject()
	{
		return mInject;
	}

	public Field getField()
	{
		return mField;
	}
}
