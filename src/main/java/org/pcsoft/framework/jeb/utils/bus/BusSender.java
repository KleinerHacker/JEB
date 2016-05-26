package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.type.ThreadFactory;
import org.pcsoft.framework.jeb.type.ThreadRunner;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverMethodDescriptor;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverQualifierKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by pfeifchr on 26.05.2016.
 */
final class BusSender {

    public static void send(List<ReceiverMethodDescriptor> receiverMethodDescriptorList, List<Annotation> qualifierAnnotationList, final Object value, JEBConfiguration configuration) {
        final ReceiverQualifierKey qualifierKey = new ReceiverQualifierKey(value.getClass(), qualifierAnnotationList);
        for (final ReceiverMethodDescriptor receiverMethodDescriptor : receiverMethodDescriptorList) {
            if (!receiverMethodDescriptor.getQualifierKey().equals(qualifierKey))
                continue;

            invokeOnThread(receiverMethodDescriptor, value, configuration, receiverMethodDescriptor.getThreadRunner(), true);
        }
    }

    private static void invokeOnThread(final ReceiverMethodDescriptor receiverMethodDescriptor, final Object value, JEBConfiguration configuration, ThreadRunner threadRunner,
                                       boolean useLocalThreadRunnerSettings) {
        switch (threadRunner) {
            case CurrentThread:
                if (useLocalThreadRunnerSettings) {
                    invokeOnThread(receiverMethodDescriptor, value, configuration, configuration.getBusConfiguration().getThreadRunner(), false);
                } else {
                    invoke(receiverMethodDescriptor, value);
                }
                break;
            case NewThread:
                ThreadFactory.getInstance(configuration).submit(new Runnable() {
                    @Override
                    public void run() {
                        invoke(receiverMethodDescriptor, value);
                    }
                });
                break;
            default:
                throw new RuntimeException();
        }
    }

    private static void invoke(ReceiverMethodDescriptor receiverMethodDescriptor, Object value) {
        try {
            receiverMethodDescriptor.getReceiverMethod().invoke(receiverMethodDescriptor.getInstance(), value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException("Unable to call remote method on event bus!", e);
        }
    }

    private BusSender() {

    }
}
