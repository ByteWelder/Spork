package spork.android.extension;

import javax.annotation.Nullable;

public interface FragmentResolver {
	/**
	 * Resolve a fragment by its id.
	 * It resolves to {@link Object} because the Fragment might be a regular Android one or a support library one.
	 *
	 * @param object either a regular or a support fragment instance
	 * @param id the Fragment id
	 * @throws Exception when something unexpected happens
	 * @return a Fragment or null
	 */
	@Nullable
	Object resolveFragment(Object object, int id) throws Exception;

	/**
	 * Resolve a fragment by an id field name.
	 * It resolves to {@link Object} because the Fragment might be a regular Android one or a support library one.
	 *
	 * @param object either a regular or a support fragment instance
	 * @param idName the Fragment id name (the "fragment_name" part in R.id.fragment_name)
	 * @throws Exception when something unexpected happens
	 * @return a Fragment or null
	 */
	@Nullable
	Object resolveFragment(Object object, String idName) throws Exception;
}
