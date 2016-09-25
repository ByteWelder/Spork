package io.github.sporklibrary.android.support.resolvers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.resolvers.FragmentResolver;
import io.github.sporklibrary.exceptions.BindException;

import static io.github.sporklibrary.android.utils.Reflection.tryCast;

public class SupportFragmentResolver implements FragmentResolver {

	@Override
	public @Nullable Object resolveFragment(Object object, int id) {
		FragmentActivity activity = tryCast(FragmentActivity.class, object);

		if (activity != null) {
			return activity.getSupportFragmentManager().findFragmentById(id);
		}

		Fragment fragment = tryCast(Fragment.class, object);

		if (fragment != null) {
			return fragment.getFragmentManager().findFragmentById(id);
		}

		return null;
	}

	@Override
	public @Nullable Object resolveFragment(Object object, String idName) {
		FragmentActivity activity = tryCast(FragmentActivity.class, object);

		if (activity != null) {
			int id = activity.getResources().getIdentifier(idName, "id", activity.getPackageName());

			if (id == 0) {
				throw new BindException(BindFragment.class, activity.getClass(), "Fragment not found by name for id '" + idName + "'");
			}

			return activity.getSupportFragmentManager().findFragmentById(id);
		}

		Fragment fragment = tryCast(Fragment.class, object);

		if (fragment != null) {
			int id = fragment.getResources().getIdentifier(idName, "id", fragment.getActivity().getPackageName());

			if (id == 0) {
				throw new BindException(BindFragment.class, fragment.getClass(), "Fragment not found by name for id '" + idName + "'");
			}

			return fragment.getFragmentManager().findFragmentById(id);
		}

		return null;
	}
}
