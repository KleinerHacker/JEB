package org.pcsoft.framework.jeb.type.listener;

import java.lang.reflect.Method;

public interface MethodVisitor {
    void onAnnotatedMethod(final Method method);
}