package org.pcsoft.framework.jeb.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Reflection Utils
 */
public final class ReflectionUtils {
    private interface ClassVisitor {
        void onVisitClass(final Class<?> clazz);
    }

    /**
     * Check that the class implements the interface
     * @param interfaceClass
     * @param clazz
     * @return
     */
    public static boolean isClassImplement(final Class<?> interfaceClass, final Class<?> clazz) {
        final AtomicBoolean result = new AtomicBoolean(false);
        visitClassInInheritance(clazz, new ClassVisitor() {
            @Override
            public void onVisitClass(Class<?> clazz) {
                for (final Class<?> currentInterfaceClass : clazz.getInterfaces()) {
                    if (currentInterfaceClass == interfaceClass) {
                        result.set(true);
                        break;
                    }
                }

            }
        });

        return result.get();
    }

    /**
     * Visit all classes in inheritance, from implementation to abstract base.
     * @param baseClass
     * @param visitor
     */
    private static void visitClassInInheritance(final Class<?> baseClass, ClassVisitor visitor) {
        Class<?> currentClass = baseClass;
        while (currentClass != null && currentClass != Object.class) {
            visitor.onVisitClass(currentClass);
            currentClass = currentClass.getSuperclass();
        }
    }

    private ReflectionUtils() {
    }
}
