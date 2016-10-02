package spork.android.interfaces;

import javax.annotation.Nullable;

public interface FragmentResolver {
	/**
	 * Resolve a fragment by its id
	 *
	 * @param object either a regular or a support fragment instance
	 * @return a Fragment or null
	 */
	@Nullable
	Object resolveFragment(Object object, int id);

	/**
	 * Resolve a fragment by an id field name
	 *
	 * @param object either a regular or a support fragment instance
	 * @return a Fragment or null
	 */
	@Nullable
	Object resolveFragment(Object object, String idName);
}
