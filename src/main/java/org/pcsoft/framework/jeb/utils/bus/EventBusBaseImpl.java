package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.EventBusManager;
import org.pcsoft.framework.jeb.RunOnThreadManager;
import org.pcsoft.framework.jeb.SurroundManager;
import org.pcsoft.framework.jeb.type.listener.MethodVisitor;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;
import org.pcsoft.framework.jeb.utils.ClassReaderUtils;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverMethodDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class EventBusBaseImpl implements EventBus {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusBaseImpl.class);

    private final EventBusManager parentEventBusManager;
    private final Map<Class<?>, List<ReceiverMethodDescriptor>> receiverClassMap = new HashMap<>();
    private final Map<ReceiverHandler, ReceiverMethodDescriptor> receiverMethodMap = new HashMap<>();

    public EventBusBaseImpl(EventBusManager parentEventBusManager) {
        this.parentEventBusManager = parentEventBusManager;
    }

    @Override
    public EventBusManager getParentEventBusManager() {
        return parentEventBusManager;
    }

    @Override
    public RunOnThreadManager getThreadRunnerManager() {
        return parentEventBusManager.getRunOnThreadManager();
    }

    @Override
    public SurroundManager getSurroundManager() {
        return parentEventBusManager.getSurroundManager();
    }

    @Override
    public void registerReceiverClass(final Object value) {
        if (receiverClassMap.containsKey(value.getClass()))
            return;

        LOGGER.info("Register Receiver Class: " + value.getClass());
        receiverClassMap.put(value.getClass(), new ArrayList<ReceiverMethodDescriptor>());
        ClassReaderUtils.readReceiverClass(value.getClass(), new MethodVisitor() {
            @Override
            public void onAnnotatedMethod(Method method) {
                receiverClassMap.get(value.getClass()).add(new ReceiverMethodDescriptor(method, value));
            }
        });
    }

    @Override
    public void unregisterReceiverClass(final Object value) {
        if (!receiverClassMap.containsKey(value.getClass()))
            return;

        LOGGER.info("Unregister Receiver Class: " + value.getClass());
        receiverClassMap.remove(value.getClass());
    }

    @Override
    public <T> void registerReceiverMethod(Class<T> parameterClass, ReceiverHandler<T> receiverHandler) {
        if (receiverMethodMap.containsKey(receiverHandler))
            return;

        LOGGER.debug("Register Receiver Method with identity hash " + System.identityHashCode(receiverHandler));
        receiverMethodMap.put(receiverHandler, new ReceiverMethodDescriptor(parameterClass, receiverHandler));
    }

    @Override
    public <T> void unregisterReceiverMethod(ReceiverHandler<T> receiverHandler) {
        if (!receiverMethodMap.containsKey(receiverHandler))
            return;

        LOGGER.debug("Unregister Receiver Method with identity hash " + System.identityHashCode(receiverHandler));
        receiverMethodMap.remove(receiverHandler);
    }

    @Override
    public void send(Object value) {
        //Do nothing, will be implemented by proxy
    }

    public List<ReceiverMethodDescriptor> getReceiverMethodDescriptorList() {
        final List<ReceiverMethodDescriptor> resultList = new ArrayList<>();
        for (final List<ReceiverMethodDescriptor> list : receiverClassMap.values()) {
            resultList.addAll(list);
        }
        resultList.addAll(receiverMethodMap.values());

        return Collections.unmodifiableList(resultList);
    }
}