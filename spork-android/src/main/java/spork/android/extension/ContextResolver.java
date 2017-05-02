package spork.android.extension;

import android.content.Context;

import javax.annotation.Nullable;

/**
 * Resolves a Context for a given object or returns null when it cannot be resolved.
 */
public interface ContextResolver {
	/**
	 * Try to resolve a Context from the given object
	 * @param object the instance to resolve a Context from
	 * @return the Context or null when none is found
	 * @throws Exception when something unexpected happens
	 */
	@Nullable
	Context resolveContext(Object object) throws Exception;
}
