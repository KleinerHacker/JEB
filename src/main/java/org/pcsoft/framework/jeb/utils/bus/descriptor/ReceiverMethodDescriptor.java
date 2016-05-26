package org.pcsoft.framework.jeb.utils.bus.descriptor;

import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.annotation.EventReceiverQualifier;
import org.pcsoft.framework.jeb.type.ThreadRunner;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class ReceiverMethodDescriptor {
    private final Method receiverMethod;
    private final ReceiverQualifierKey qualifierKey;
    private final ThreadRunner threadRunner;

    private final Object instance;

    public <T>ReceiverMethodDescriptor(final Class<T> parameterClass, final ReceiverHandler<T> receiverHandler, final ThreadRunner threadRunner) {
        final Method method;
        try {
            method = receiverHandler.getClass().getMethod("onReceive", parameterClass);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Unable to find Method!", e);
        }
        final Annotation[] annotations = method.getAnnotations();
        final List<Annotation> qualifierAnnotationList = new ArrayList<>();
        for (final Annotation annotation : annotations) {
            if (annotation.getClass().getAnnotation(EventReceiverQualifier.class) == null)
                continue;
            qualifierAnnotationList.add(annotation);
        }

        this.receiverMethod = method;
        this.threadRunner = threadRunner;
        this.qualifierKey = new ReceiverQualifierKey(parameterClass, qualifierAnnotationList);

        this.instance = receiverHandler;
    }

    public ReceiverMethodDescriptor(final Method method, final Object instance) {
        if (method.getAnnotation(EventReceiver.class) == null)
            throw new IllegalArgumentException("Method without annotation " + EventReceiver.class.getName() + ": " + method.toString());
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        if (method.getParameterTypes().length != 1)
            throw new IllegalArgumentException("Method with not exact one parameter: " + method.toString());

        final Annotation[] annotations = method.getAnnotations();
        final List<Annotation> qualifierAnnotationList = new ArrayList<>();
        for (final Annotation annotation : annotations) {
            if (annotation.getClass().getAnnotation(EventReceiverQualifier.class) == null)
                continue;
            qualifierAnnotationList.add(annotation);
        }

        this.receiverMethod = method;
        this.threadRunner = method.getAnnotation(EventReceiver.class).runOnThread();
        this.qualifierKey = new ReceiverQualifierKey(method.getParameterTypes()[0], qualifierAnnotationList);

        this.instance = instance;
    }

    public ReceiverQualifierKey getQualifierKey() {
        return qualifierKey;
    }

    public Method getReceiverMethod() {
        return receiverMethod;
    }

    public Object getInstance() {
        return instance;
    }

    public ThreadRunner getThreadRunner() {
        return threadRunner;
    }
}
