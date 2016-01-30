package io.github.sporklibrary.test.bindfragment;

import android.app.Fragment;

public interface FragmentProvider
{
	Fragment getFragmentByIdSpecified();
	Fragment getFragmentByIdImplied();
}
