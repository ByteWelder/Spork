package spork.android.internal.utils;

import android.content.Context;
import android.view.View;

import spork.android.BindView;
import spork.android.extension.ViewResolver;

import static spork.BindFailedBuilder.bindFailedBuilder;

public final class Views {

	private Views() {
	}

	/**
	 * @param viewResolver resolves view from an unknown parent object
	 * @param viewId       R.id.* value or ResourceId.NONE
	 * @param nameFallback used when ResourceId.NONE is set, this name will be used to resolve R.id.namefallback
	 * @param object       any Activity, Fragment or View (including support library types)
	 * @return the found View
	 */
	public static View getView(ViewResolver viewResolver, int viewId, String nameFallback, Object object) {
		View rootView = viewResolver.resolveView(object);
		if (rootView == null) {
			throw bindFailedBuilder(BindView.class, "cannot resolve View from specified class")
					.into(object.getClass())
					.build();
		}

		int searchViewId;
		if (viewId == ResourceId.NONE) {
			// find by name
			Context context = rootView.getContext();

			searchViewId = context.getResources().getIdentifier(nameFallback, "id", context.getPackageName());
			if (searchViewId == 0) {
				throw bindFailedBuilder(BindView.class, "View not found for fallback R.id." + nameFallback)
						.into(object.getClass())
						.build();
			}
		} else {
			searchViewId = viewId;
		}

		View view = rootView.findViewById(searchViewId);
		if (view == null) {
			throw bindFailedBuilder(BindView.class, "View not found")
					.into(object.getClass())
					.build();
		}

		return view;
	}
}
