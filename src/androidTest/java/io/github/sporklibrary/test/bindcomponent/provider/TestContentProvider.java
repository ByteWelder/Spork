package io.github.sporklibrary.test.bindcomponent.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class TestContentProvider extends ContentProvider
{
	@BindComponent
	private TestComponent mTestComponent;

	@Override
	public boolean onCreate()
	{
		Spork.bind(this);

		return true;
	}

	@Override
	public @Nullable Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		return null;
	}

	@Override
	public @Nullable String getType(@NonNull Uri uri)
	{
		return null;
	}

	@Override
	public @Nullable Uri insert(@NonNull Uri uri, ContentValues values)
	{
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
	{
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		return 0;
	}

	public TestComponent getTestComponent()
	{
		return mTestComponent;
	}
}
