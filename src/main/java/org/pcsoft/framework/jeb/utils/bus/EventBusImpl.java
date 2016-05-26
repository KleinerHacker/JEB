package org.pcsoft.framework.jeb.utils.bus;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.type.ThreadRunner;
import org.pcsoft.framework.jeb.type.listener.MethodVisitor;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;
import org.pcsoft.framework.jeb.utils.ClassReaderUtils;
import org.pcsoft.framework.jeb.utils.bus.descriptor.ReceiverMethodDescriptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class EventBusImpl implements EventBus {
    private final JEBConfiguration configuration;
    private final Map<Class<?>, List<ReceiverMethodDescriptor>> receiverClassMap = new HashMap<>();
    private final Map<ReceiverHandler, ReceiverMethodDescriptor> receiverMethodMap = new HashMap<>();

    public EventBusImpl(JEBConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public JEBConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void registerReceiverClass(final Object value) {
        if (receiverClassMap.containsKey(value.getClass()))
            return;

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

        receiverClassMap.remove(value.getClass());
    }

    @Override
    public <T> void registerReceiverMethod(Class<T> parameterClass, ReceiverHandler<T> receiverHandler) {
        registerReceiverMethod(parameterClass, receiverHandler, ThreadRunner.CurrentThread);
    }

    @Override
    public <T> void registerReceiverMethod(Class<T> parameterClass, ReceiverHandler<T> receiverHandler, ThreadRunner threadRunner) {
        if (receiverMethodMap.containsKey(receiverHandler))
            return;

        receiverMethodMap.put(receiverHandler, new ReceiverMethodDescriptor(parameterClass, receiverHandler, threadRunner));
    }

    @Override
    public <T> void unregisterReceiverMethod(ReceiverHandler<T> receiverHandler) {
        if (!receiverMethodMap.containsKey(receiverHandler))
            return;

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