package io.github.sporklibrary.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.sporklibrary.BinderManager;
import io.github.sporklibrary.android.binders.BindClickBinder;
import io.github.sporklibrary.android.binders.BindFragmentBinder;
import io.github.sporklibrary.android.binders.BindLayoutBinder;
import io.github.sporklibrary.android.binders.BindResourceBinder;
import io.github.sporklibrary.android.binders.BindViewBinder;
import io.github.sporklibrary.android.resolvers.ContextResolverManager;
import io.github.sporklibrary.android.resolvers.DefaultContextResolver;
import io.github.sporklibrary.android.resolvers.DefaultFragmentResolver;
import io.github.sporklibrary.android.resolvers.DefaultViewResolver;
import io.github.sporklibrary.android.resolvers.FragmentResolverManager;
import io.github.sporklibrary.android.resolvers.ViewResolverManager;

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

        ViewResolverManager.shared().addViewResolver(new DefaultViewResolver());
        ContextResolverManager.shared().addContextResolver(new DefaultContextResolver());
        FragmentResolverManager.shared().addFragmentResolver(new DefaultFragmentResolver());

        tryInitializeSupportBindings();
    }

    private static void tryInitializeSupportBindings() {
        try {
            Class<?> e = Class.forName("io.github.sporklibrary.android.support.SporkAndroidSupport");
            Method initializeMethod = e.getDeclaredMethod("initialize");
            initializeMethod.invoke(null);
            System.out.println("Spork Android: initialized with Support library bindings");
        } catch (ClassNotFoundException var3) {
            System.out.println("Spork Android: initialized without Support library bindings");
        } catch (NoSuchMethodException var4) {
            System.out.println("Spork Android: Spork Android Support library found, but initialize method is not present");
        } catch (InvocationTargetException var5) {
            System.out.println("Spork Android: Spork Android Support library found, but initialization failed because of InvocationTargetException: " + var5.getMessage());
        } catch (IllegalAccessException var6) {
            System.out.println("Spork Android: Spork Android Support library found, but initialization failed because of IllegalAccessException: " + var6.getMessage());
        }

    }
}
