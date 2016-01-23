package net.kenvanhoeylandt.spork.component;

import net.kenvanhoeylandt.spork.annotations.Component;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentRetriever
{
	private static final Logger sLogger = LoggerFactory.getLogger(ComponentRetriever.class);

	private final Map<Class<?>, ComponentClass> mMap = new HashMap<>();

	public ComponentRetriever(Package pkg)
	{
		String namespace = pkg.getName();

		Set<Class<?>> annotated_types = new Reflections(namespace).getTypesAnnotatedWith(Component.class);

		for (Class<?> component_type : annotated_types)
		{
			Component component = component_type.getAnnotation(Component.class);

			mMap.put(component_type, new ComponentClass(component, component_type));
		}

		sLogger.debug("found {} type component for {}", mMap.size(), namespace);
		// TODO: implement method annotations for Component
//		Reflections reflections = new Reflections(new ConfigurationBuilder()
//			.setUrls(ClasspathHelper.forPackage(namespace))
//			.setScanners(new MethodAnnotationsScanner()));
//
//		mCachedMethodComponents = reflections.getMethodsAnnotatedWith(Component.class);
	}

	public @Nullable ComponentClass get(Class<?> classObject)
	{
		return mMap.get(classObject);
	}
}
