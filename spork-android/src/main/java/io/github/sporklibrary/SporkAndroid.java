package io.github.sporklibrary;

import io.github.sporklibrary.binders.*;

public final class SporkAndroid {

    /**
     * @deprecated Initialization is now done automatically, you don't have to use this method anymore.
     */
    @Deprecated
    public static void initialize() {
    }

    /**
     * Register all binders to a specific BinderManager.
     * Warning: Do not call this manually. This is called automatically by the Spork core libraries.
     *
     * @param binderManager the binder manager to register to
     */
    public static void initialize(BinderManager binderManager) {
        binderManager.register(new BindLayoutBinder()); // layouts must be bound before views
        binderManager.register(new BindViewBinder());
        binderManager.register(new BindFragmentBinder());
        binderManager.register(new BindClickBinder());
        binderManager.register(new BindResourceBinder());
    }
}
