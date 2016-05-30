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
 * Descriptor for a receiver method (contains {@link ReceiverHandler}-Methods too, see second constructor)
 */
public final class ReceiverMethodDescriptor {
    private final Method receiverMethod;
    private final ReceiverQualifierKey qualifierKey;
    private final Annotation runOnThreadAnnotation;
    private final List<Annotation> surroundAnnotationList = new ArrayList<>();

    private final Object instance;

    /**
     * Construct the descriptor from a {@link ReceiverHandler}.
     * @param parameterClass
     * @param receiverHandler
     * @param <T>
     */
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
        this.runOnThreadAnnotation = AnnotationReaderUtils.findRunOnThreadAnnotation(method, false);
        this.qualifierKey = new ReceiverQualifierKey(parameterClass, qualifierAnnotationList);
        this.surroundAnnotationList.addAll(AnnotationReaderUtils.findSurroundActionAnnotations(method));

        this.instance = receiverHandler;
    }

    /**
     * Create the descriptor from a receiver method, annotated with {@link EventReceiver}
     * @param method
     * @param instance
     */
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
        this.runOnThreadAnnotation = AnnotationReaderUtils.findRunOnThreadAnnotation(method, false);
        this.qualifierKey = new ReceiverQualifierKey(method.getParameterTypes()[0], qualifierAnnotationList);
        this.surroundAnnotationList.addAll(AnnotationReaderUtils.findSurroundActionAnnotations(method));

        this.instance = instance;
    }

    /**
     * Qualifier-Key to find method if send via {@link org.pcsoft.framework.jeb.EventBus}
     * @return
     */
    public ReceiverQualifierKey getQualifierKey() {
        return qualifierKey;
    }

    /**
     * The receiver method
     * @return
     */
    public Method getReceiverMethod() {
        return receiverMethod;
    }

    /**
     * Instance of the class with receiver method
     * @return
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * Returns the optional Run-On-Thread Qualifier Annotation or NULL
     * @return
     */
    public Annotation getRunOnThreadAnnotation() {
        return runOnThreadAnnotation;
    }

    /**
     * A list of all surround qualifier annotations. Can be empty.
     * @return
     */
    public List<Annotation> getSurroundAnnotationList() {
        return Collections.unmodifiableList(surroundAnnotationList);
    }
}
