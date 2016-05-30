package org.pcsoft.framework.jeb.utils;

import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.type.listener.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class Reader Utils
 */
public final class ClassReaderUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassReaderUtils.class);

    /**
     * Read the receiver class and visit all methods that annotated with {@link EventReceiver}
     * @param clazz
     * @param methodVisitor
     */
    public static void readReceiverClass(final Class<?> clazz, MethodVisitor methodVisitor) {
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            final Method[] declaredMethods = currentClass.getDeclaredMethods();
            for (final Method method : declaredMethods) {
                if (method.getAnnotation(EventReceiver.class) == null)
                    continue;
                if (!Modifier.isPublic(method.getModifiers())) {
                    method.setAccessible(true);
                }
                if (method.getParameterTypes().length != 1)
                    throw new IllegalStateException("Unable to handle method " + method.toString() + ": Only exact one parameter is expected!");
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("> Find method: " + method.toString());
                }
                methodVisitor.onAnnotatedMethod(method);
            }

            currentClass = currentClass.getSuperclass();
        }
    }

    private ClassReaderUtils() {

    }
}
