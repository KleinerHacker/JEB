package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.EventBusManager;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverMethodDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Represent an {@link EventBus}-Builder. It creates a proxy based on event bus interface.
 */
public final class EventBusBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusBuilder.class);

    private static final class EventBussProxy implements InvocationHandler {
        private final EventBus eventBus;

        public EventBussProxy(EventBusManager eventBusManager) {
            this.eventBus = new EventBusBaseImpl(eventBusManager);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //Handle basics with default implementation
            if (method.getName().equals("getParentEventBusManager") && method.getDeclaringClass() == EventBus.class) {
                return this.eventBus.getParentEventBusManager();
            } else if (method.getName().equals("getRunOnThreadManager") && method.getDeclaringClass() == EventBus.class) {
                return this.eventBus.getRunOnThreadManager();
            } else if (method.getName().equals("getSurroundManager") && method.getDeclaringClass() == EventBus.class) {
                return this.eventBus.getSurroundManager();
            } else if (method.getName().equals("registerReceiverClass") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.registerReceiverClass(args[0]);
                return null;
            } else if (method.getName().equals("unregisterReceiverClass") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.unregisterReceiverClass(args[0]);
                return null;
            } else if (method.getName().equals("registerReceiverMethod") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.registerReceiverMethod((Class) args[0], (ReceiverHandler) args[1]);
                return null;
            } else if (method.getName().equals("unregisterReceiverMethod") && method.getDeclaringClass() == EventBus.class) {
                this.eventBus.unregisterReceiverMethod((ReceiverHandler) args[0]);
                return null;
            } else {
                //Handle special sender methods
                if (method.getAnnotation(CustomEventBus.Sender.class) != null) {
                    runSendMethod(method, args[0]);
                }
                return null;
            }
        }

        /**
         * Run the sending algorithm, based on annotations
         * @param method
         * @param arg
         */
        private void runSendMethod(Method method, Object arg) {
            if (method.getAnnotation(CustomEventBus.Sender.class) == null)
                throw new IllegalArgumentException("Method is not a send method: " + method.toString());
            if (!Modifier.isPublic(method.getModifiers()))
                throw new IllegalStateException("Sender method must be public: " + method.toString());
            if (method.getParameterTypes().length != 1)
                throw new IllegalStateException("Not exact one parameter was found on sender method: " + method.toString());

            final List<ReceiverMethodDescriptor> receiverMethodDescriptorList = ((EventBusBaseImpl) eventBus).getReceiverMethodDescriptorList();
            LOGGER.info("Invoke send method(s) on Receiver Classes and Receiver Handlers for Event Bus: " + method.getDeclaringClass().getName());
            BusSender.send(eventBus, method, receiverMethodDescriptorList, arg);
        }
    }

    /**
     * Build a new event {@link EventBus} based on the given event bus interface.
     * @param eventBusClass
     * @param eventBusManager
     * @param <T>
     * @return
     */
    public static <T extends EventBus> T build(final Class<T> eventBusClass, final EventBusManager eventBusManager) {
        LOGGER.debug("Build new event bus, based on class: " + eventBusClass.getName());
        return (T) Proxy.newProxyInstance(EventBusBuilder.class.getClassLoader(), new Class[]{EventBus.class, eventBusClass}, new EventBussProxy(eventBusManager));
    }

}
