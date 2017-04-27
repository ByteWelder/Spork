package spork.matchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public enum AccessModifier {
	PUBLIC,
	PROTECTED,
	PRIVATE,
	DEFAULT;

	private static AccessModifier accessModifierOf(int modifiers) {
		if (Modifier.isPublic(modifiers)) {
			return PUBLIC;
		} else if (Modifier.isProtected(modifiers)) {
			return PROTECTED;
		} else if (Modifier.isPrivate(modifiers)) {
			return PRIVATE;
		} else {
			return DEFAULT;
		}
	}

	public static AccessModifier accessModifierOf(Constructor constructor) {
		return accessModifierOf(constructor.getModifiers());
	}

	public static AccessModifier accessModifierOf(Field field) {
		return accessModifierOf(field.getModifiers());
	}

	public static AccessModifier accessModifierOf(Method method) {
		return accessModifierOf(method.getModifiers());
	}
}
