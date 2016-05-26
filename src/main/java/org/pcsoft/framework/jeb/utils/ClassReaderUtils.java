package org.pcsoft.framework.jeb.utils;

import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.type.listener.MethodVisitor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class ClassReaderUtils {
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
                methodVisitor.onAnnotatedMethod(method);
            }

            currentClass = currentClass.getSuperclass();
        }
    }

    private ClassReaderUtils() {

    }
}
