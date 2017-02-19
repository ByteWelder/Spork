package spork.android.interfaces;

import javax.annotation.Nullable;

public interface FragmentResolver {
	/**
	 * Resolve a fragment by its id.
	 * </p>
	 * It resolves to {@link Object} because the Fragment might be a regular Android one or a support library one.
	 *
	 * @param object either a regular or a support fragment instance
	 * @return a Fragment or null
	 */
	@Nullable
	Object resolveFragment(Object object, int id);

	/**
	 * Resolve a fragment by an id field name.
	 * </p>
	 * It resolves to {@link Object} because the Fragment might be a regular Android one or a support library one.
	 *
	 * @param object either a regular or a support fragment instance
	 * @return a Fragment or null
	 */
	@Nullable
	Object resolveFragment(Object object, String idName);
}
