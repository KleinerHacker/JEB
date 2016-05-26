package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.annotation.EventReceiverQualifier;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.type.ThreadRunner;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverMethodDescriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class EventBusBuilder {
    private static final class EventBussProxy implements InvocationHandler {
        private final EventBus eventBus;

        public EventBussProxy(JEBConfiguration configuration) {
            this.eventBus = new EventBusImpl(configuration);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getConfiguration") && method.getDeclaringClass() == EventBus.class) {
                return this.eventBus.getConfiguration();
            } else if (method.getName().equals("registerReceiverClass") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.registerReceiverClass(args[0]);
                return null;
            } else if (method.getName().equals("unregisterReceiverClass") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.unregisterReceiverClass(args[0]);
                return null;
            } else if (method.getName().equals("registerReceiverMethod") && method.getDeclaringClass() == EventBus.class) {
                if (method.getParameterTypes().length == 3) {
                    this.eventBus.registerReceiverMethod((Class) args[0], (ReceiverHandler) args[1], (ThreadRunner) args[2]);
                } else {
                    this.eventBus.registerReceiverMethod((Class) args[0], (ReceiverHandler) args[1]);
                }
                return null;
            } else if (method.getName().equals("unregisterReceiverMethod") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.unregisterReceiverMethod((ReceiverHandler) args[0]);
                return null;
            } else {
                if (method.getAnnotation(CustomEventBus.Sender.class) != null) {
                    runSendMethod(method, args[0]);
                }
                return null;
            }
        }

        private void runSendMethod(Method method, Object arg) {
            if (method.getAnnotation(CustomEventBus.Sender.class) == null)
                throw new IllegalArgumentException("Method is not a send method: " + method.toString());
            if (!Modifier.isPublic(method.getModifiers()))
                throw new IllegalStateException("Sender method must be public: " + method.toString());
            if (method.getParameterTypes().length != 1)
                throw new IllegalStateException("Not exact one parameter was found on sender method: " + method.toString());

            final Annotation[] annotations = method.getAnnotations();
            final List<Annotation> qualifierAnnotationList = new ArrayList<>();
            for (final Annotation annotation : annotations) {
                if (annotation.getClass().getAnnotation(EventReceiverQualifier.class) == null)
                    continue;
                ;
                qualifierAnnotationList.add(annotation);
            }

            final List<ReceiverMethodDescriptor> receiverMethodDescriptorList = ((EventBusImpl) eventBus).getReceiverMethodDescriptorList();
            BusSender.send(receiverMethodDescriptorList, qualifierAnnotationList, arg, eventBus.getConfiguration());
        }
    }

    public static <T extends EventBus> T build(final Class<T> eventBusClass, final JEBConfiguration configuration) {
        return (T) Proxy.newProxyInstance(EventBusBuilder.class.getClassLoader(), new Class[]{EventBus.class, eventBusClass}, new EventBussProxy(configuration));
    }

}
