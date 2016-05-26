package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.type.ThreadRunner;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public interface EventBus {
    JEBConfiguration getConfiguration();

    void registerReceiverClass(final Object value);
    void unregisterReceiverClass(final Object value);

    <T>void registerReceiverMethod(final Class<T> parameterClass, final ReceiverHandler<T> receiverHandler, final ThreadRunner threadRunner);
    <T>void registerReceiverMethod(final Class<T> parameterClass, final ReceiverHandler<T> receiverHandler);
    <T>void unregisterReceiverMethod(final ReceiverHandler<T> receiverHandler);

    @CustomEventBus.Sender
    void send(Object value);
}