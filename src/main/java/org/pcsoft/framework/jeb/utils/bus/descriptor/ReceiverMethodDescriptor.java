package org.pcsoft.framework.jeb.utils.bus.descriptor;

import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;
import org.pcsoft.framework.jeb.utils.AnnotationReaderUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class ReceiverMethodDescriptor {
    private final Method receiverMethod;
    private final ReceiverQualifierKey qualifierKey;
    private final Annotation threadRunnerAnnotation;
    private final List<Annotation> surroundAnnotationList = new ArrayList<>();

    private final Object instance;

    public <T>ReceiverMethodDescriptor(final Class<T> parameterClass, final ReceiverHandler<T> receiverHandler) {
        final Method method;
        try {
            method = receiverHandler.getClass().getMethod("onReceive", parameterClass);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Unable to find Method!", e);
        }
        final List<Annotation> qualifierAnnotationList = AnnotationReaderUtils.findQualifierAnnotations(method);

        this.receiverMethod = method;
        this.threadRunnerAnnotation = AnnotationReaderUtils.findThreadRunnerAnnotation(method, false);
        this.qualifierKey = new ReceiverQualifierKey(parameterClass, qualifierAnnotationList);
        this.surroundAnnotationList.addAll(AnnotationReaderUtils.findSurroundActionAnnotations(method));

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

        final List<Annotation> qualifierAnnotationList = AnnotationReaderUtils.findQualifierAnnotations(method);

        this.receiverMethod = method;
        this.threadRunnerAnnotation = AnnotationReaderUtils.findThreadRunnerAnnotation(method, false);
        this.qualifierKey = new ReceiverQualifierKey(method.getParameterTypes()[0], qualifierAnnotationList);
        this.surroundAnnotationList.addAll(AnnotationReaderUtils.findSurroundActionAnnotations(method));

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

    public Annotation getThreadRunnerAnnotation() {
        return threadRunnerAnnotation;
    }

    public List<Annotation> getSurroundAnnotationList() {
        return Collections.unmodifiableList(surroundAnnotationList);
    }
}
