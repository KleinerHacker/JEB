package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThread;
import org.pcsoft.framework.jeb.annotation.handler.SurroundAction;
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
 * Created by pfeifchr on 26.05.2016.
 */
final class BusSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusSender.class);

    public static void send(EventBus eventBus, Method sendMethod, List<ReceiverMethodDescriptor> receiverMethodDescriptorList, final Object value) {
        final List<Annotation> qualifierAnnotationList = AnnotationReaderUtils.findQualifierAnnotations(sendMethod);
        final ReceiverQualifierKey qualifierKey = new ReceiverQualifierKey(value.getClass(), qualifierAnnotationList);
        for (final ReceiverMethodDescriptor receiverMethodDescriptor : receiverMethodDescriptorList) {
            if (!receiverMethodDescriptor.getQualifierKey().equals(qualifierKey))
                continue;

            LOGGER.debug("> Send to method: " + receiverMethodDescriptor.getReceiverMethod());
            Annotation threadRunnerAnnotation = receiverMethodDescriptor.getThreadRunnerAnnotation();
            if (threadRunnerAnnotation == null) {
                threadRunnerAnnotation = AnnotationReaderUtils.findThreadRunnerAnnotation(sendMethod, true);
            }

            if (threadRunnerAnnotation == null) {
                LOGGER.trace("> On Current Thread: " + Thread.currentThread().getName());
                invoke(eventBus, receiverMethodDescriptor, value);
            } else {
                invokeOnThread(eventBus, threadRunnerAnnotation, receiverMethodDescriptor, value);
            }
        }
    }

    private static void invokeOnThread(final EventBus eventBus, final Annotation threadRunnerAnnotation, final ReceiverMethodDescriptor receiverMethodDescriptor, final Object value) {
        if (threadRunnerAnnotation == null)
            throw new IllegalArgumentException();

        LOGGER.trace("> On Other Thread");
        final RunOnThread runOnThread = eventBus.getThreadRunnerManager().getRunOnThreadInstanceFor(threadRunnerAnnotation);
        if (runOnThread == null)
            throw new JEBInvokeException("Unable to find a Run On Thread Instance for Annotation '" + threadRunnerAnnotation.annotationType().getName() +
                    "'. Is Run On Thread Class registered?");
        runOnThread.runOnThread(threadRunnerAnnotation, new RunOnThread.MethodInvokeListener() {
            @Override
            public void onInvokeMethod() {
                invoke(eventBus, receiverMethodDescriptor, value);
            }
        });
    }

    private static void invoke(final EventBus eventBus, ReceiverMethodDescriptor receiverMethodDescriptor, Object value) {
        final List<Map.Entry<Annotation, SurroundAction>> surroundActionInstanceList = new ArrayList<>();
        for (final Annotation annotation : receiverMethodDescriptor.getSurroundAnnotationList()) {
            final SurroundAction surroundActionInstance = eventBus.getSurroundManager().getSurroundActionInstanceFor(annotation.getClass());
            if (surroundActionInstance == null)
                throw new JEBInvokeException("Unable to find a Surround Action Instance for Annotation '" + annotation.annotationType().getName() +
                "'. Is Surround Action Class registered?");
            surroundActionInstanceList.add(new AbstractMap.SimpleEntry<>(annotation, surroundActionInstance));
        }

        try {
            for (final Map.Entry<Annotation, SurroundAction> surroundAction : surroundActionInstanceList) {
                surroundAction.getValue().onPreInvoke(surroundAction.getKey(), receiverMethodDescriptor.getReceiverMethod(), value);
            }
            receiverMethodDescriptor.getReceiverMethod().invoke(receiverMethodDescriptor.getInstance(), value);
            for (final Map.Entry<Annotation, SurroundAction> surroundAction : surroundActionInstanceList) {
                surroundAction.getValue().onPostInvoke(surroundAction.getKey(), receiverMethodDescriptor.getReceiverMethod(), value);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new JEBInvokeException("Unable to call remote method on event bus!", e);
        }
    }

    private BusSender() {

    }
}
