package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;
import org.pcsoft.framework.jeb.exception.JEBInvokeException;
import org.pcsoft.framework.jeb.utils.AnnotationReaderUtils;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverMethodDescriptor;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverQualifierKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represent an utils class to send a request to all receiver methods
 */
final class BusSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusSender.class);

    /**
     * Send a request with the given event bus and send method to all listed receiver methods.
     * @param eventBus Based event bus
     * @param sendMethod Sender Method in Event Bus
     * @param receiverMethodDescriptorList List of receiver methods
     * @param value Value to set in method call
     */
    public static void send(EventBus eventBus, Method sendMethod, List<ReceiverMethodDescriptor> receiverMethodDescriptorList, final Object value) {
        final List<Annotation> qualifierAnnotationList = AnnotationReaderUtils.findQualifierAnnotations(sendMethod);
        final ReceiverQualifierKey qualifierKey = new ReceiverQualifierKey(value.getClass(), qualifierAnnotationList);
        for (final ReceiverMethodDescriptor receiverMethodDescriptor : receiverMethodDescriptorList) {
            if (!receiverMethodDescriptor.getQualifierKey().equals(qualifierKey))
                continue;

            LOGGER.debug("> Send to method: " + receiverMethodDescriptor.getReceiverMethod());
            Annotation threadRunnerAnnotation = receiverMethodDescriptor.getRunOnThreadAnnotation();
            if (threadRunnerAnnotation == null) {
                threadRunnerAnnotation = AnnotationReaderUtils.findRunOnThreadAnnotation(sendMethod, true);
            }

            if (threadRunnerAnnotation == null) {
                LOGGER.trace("> On Current Thread: " + Thread.currentThread().getName());
                invoke(eventBus, receiverMethodDescriptor, value);
            } else {
                invokeOnThread(eventBus, threadRunnerAnnotation, receiverMethodDescriptor, value);
            }
        }
    }

    /**
     * Invoke on a new thread (only for {@link org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier})
     * @param eventBus
     * @param threadRunnerAnnotation
     * @param receiverMethodDescriptor
     * @param value
     */
    private static void invokeOnThread(final EventBus eventBus, final Annotation threadRunnerAnnotation, final ReceiverMethodDescriptor receiverMethodDescriptor, final Object value) {
        if (threadRunnerAnnotation == null)
            throw new IllegalArgumentException();

        LOGGER.trace("> On Other Thread");
        final RunOnThreadHandler runOnThreadHandler = eventBus.getRunOnThreadManager().getRunOnThreadHandlerInstanceFor(threadRunnerAnnotation);
        if (runOnThreadHandler == null)
            throw new JEBInvokeException("Unable to find a Run On Thread Instance for Annotation '" + threadRunnerAnnotation.annotationType().getName() +
                    "'. Is Run On Thread Class registered?");
        runOnThreadHandler.runOnThread(threadRunnerAnnotation, new RunOnThreadHandler.MethodInvokeListener() {
            @Override
            public void onInvokeMethod() {
                invoke(eventBus, receiverMethodDescriptor, value);
            }
        });
    }

    /**
     * Invoke methods
     * @param eventBus
     * @param receiverMethodDescriptor
     * @param value
     */
    private static void invoke(final EventBus eventBus, ReceiverMethodDescriptor receiverMethodDescriptor, Object value) {
        final List<Map.Entry<Annotation, SurroundHandler>> surroundActionInstanceList = new ArrayList<>();
        for (final Annotation annotation : receiverMethodDescriptor.getSurroundAnnotationList()) {
            final SurroundHandler surroundHandlerInstance = eventBus.getSurroundManager().getSurroundHandlerInstanceFor(annotation.getClass());
            if (surroundHandlerInstance == null)
                throw new JEBInvokeException("Unable to find a Surround Action Instance for Annotation '" + annotation.annotationType().getName() +
                "'. Is Surround Action Class registered?");
            surroundActionInstanceList.add(new AbstractMap.SimpleEntry<>(annotation, surroundHandlerInstance));
        }

        try {
            for (final Map.Entry<Annotation, SurroundHandler> surroundAction : surroundActionInstanceList) {
                surroundAction.getValue().onPreInvoke(surroundAction.getKey(), receiverMethodDescriptor.getReceiverMethod(), value);
            }
            receiverMethodDescriptor.getReceiverMethod().invoke(receiverMethodDescriptor.getInstance(), value);
            for (final Map.Entry<Annotation, SurroundHandler> surroundAction : surroundActionInstanceList) {
                surroundAction.getValue().onPostInvoke(surroundAction.getKey(), receiverMethodDescriptor.getReceiverMethod(), value);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new JEBInvokeException("Unable to call remote method on event bus!", e);
        }
    }

    private BusSender() {

    }
}
