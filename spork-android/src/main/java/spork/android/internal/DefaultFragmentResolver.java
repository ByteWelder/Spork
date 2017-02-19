package spork.android.internal;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;

import javax.annotation.Nullable;

import spork.android.BindFragment;
import spork.android.interfaces.FragmentResolver;
import spork.android.internal.utils.Reflection;
import spork.BindException;

public class DefaultFragmentResolver implements FragmentResolver {

	@Override
	@Nullable
	public Object resolveFragment(Object object, int id) {
		Activity activity = Reflection.tryCast(Activity.class, object);

		if (activity != null) {
			return activity.getFragmentManager().findFragmentById(id);
		}

		Fragment fragment = Reflection.tryCast(Fragment.class, object);

		if (fragment != null) {
			Fragment childFragment = fragment.getFragmentManager().findFragmentById(id);

			// On a Wiko Sunset handset, the child fragment was registered with the regular FragmentManager:
			if (childFragment == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				return fragment.getChildFragmentManager().findFragmentById(id);
			}

			return childFragment;
		}

		return null;
	}

	@Override
	@Nullable
	public Object resolveFragment(Object object, String idName) {
		Activity activity = Reflection.tryCast(Activity.class, object);

		if (activity != null) {
			int id = activity.getResources().getIdentifier(idName, "id", activity.getPackageName());

			if (id == 0) {
				throw new BindException(BindFragment.class, activity.getClass(), "Fragment not found by name for id '" + idName + "'");
			}

			return activity.getFragmentManager().findFragmentById(id);
		}

		Fragment fragment = Reflection.tryCast(Fragment.class, object);

		if (fragment != null) {
			int id = fragment.getResources().getIdentifier(idName, "id", fragment.getActivity().getPackageName());

			if (id == 0) {
				throw new BindException(BindFragment.class, fragment.getClass(), "Fragment not found by name for id '" + idName + "'");
			}

			Fragment childFragment = fragment.getFragmentManager().findFragmentById(id);

			// On a Wiko Sunset handset, the child fragment was registered with the regular FragmentManager:
			if (childFragment == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				return fragment.getChildFragmentManager().findFragmentById(id);
			}

			return childFragment;
		}

		return null;
	}
}
