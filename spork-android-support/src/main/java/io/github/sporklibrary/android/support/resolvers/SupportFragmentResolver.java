package io.github.sporklibrary.android.support.resolvers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.interfaces.FragmentResolver;

import static io.github.sporklibrary.utils.Reflection.tryCast;

public class SupportFragmentResolver implements FragmentResolver {

    @Override
    public @Nullable Object resolveFragment(Object object, int id) {
        AppCompatActivity activity = tryCast(AppCompatActivity.class, object);

        if (activity != null) {
            return activity.getFragmentManager().findFragmentById(id);
        }

        Fragment fragment = tryCast(Fragment.class, object);

        if (fragment != null) {
            Fragment childFragment = fragment.getFragmentManager().findFragmentById(id);

            // On a Wiko Sunset handset, the child fragment was registered with the regular FragmentManager:
            if (childFragment == null) {
                return fragment.getChildFragmentManager().findFragmentById(id);
            }

            return childFragment;
        }

        return null;
    }

    @Override
    public @Nullable Object resolveFragment(Object object, String idName) {
        Activity activity = tryCast(Activity.class, object);

        if (activity != null) {
            int id = activity.getResources().getIdentifier(idName, "id", activity.getPackageName());

            if (id == 0) {
                throw new BindException(BindFragment.class, activity.getClass(), "Fragment not found by name for id '" + idName + "'");
            }

            return activity.getFragmentManager().findFragmentById(id);
        }

        Fragment fragment = tryCast(Fragment.class, object);

        if (fragment != null) {
            int id = fragment.getResources().getIdentifier(idName, "id", fragment.getActivity().getPackageName());

            if (id == 0) {
                throw new BindException(BindFragment.class, fragment.getClass(), "Fragment not found by name for id '" + idName + "'");
            }

            Fragment childFragment = fragment.getFragmentManager().findFragmentById(id);

            // On a Wiko Sunset handset, the child fragment was registered with the regular FragmentManager:
            if (childFragment == null) {
                return fragment.getChildFragmentManager().findFragmentById(id);
            }

            return childFragment;
        }

        return null;
    }
}
