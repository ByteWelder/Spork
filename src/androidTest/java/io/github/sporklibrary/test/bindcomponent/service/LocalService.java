package io.github.sporklibrary.test.bindcomponent.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class LocalService extends Service
{
	@BindComponent
	private TestComponent mTestComponent;

	public class LocalBinder extends Binder
	{
		public LocalService getLocalService()
		{
			return LocalService.this;
		}
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		Spork.bind(this);
	}

	@Override
	public @Nullable IBinder onBind(Intent intent)
	{
		return new LocalBinder();
	}

	public TestComponent getTestComponent()
	{
		return mTestComponent;
	}
}
