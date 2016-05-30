package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;

/**
 * Represent the event bus base interface.
 */
public interface EventBus {
    /**
     * Manager for event bus system
     * @return
     */
    EventBusManager getParentEventBusManager();

    /**
     * Manager for Run-On-Thread system
     * @return
     */
    RunOnThreadManager getRunOnThreadManager();

    /**
     * Manager for surround system
     * @return
     */
    SurroundManager getSurroundManager();

    /**
     * Register a new receiver class
     * @param value
     */
    void registerReceiverClass(final Object value);

    /**
     * Unregister a receiver class
     * @param value
     */
    void unregisterReceiverClass(final Object value);

    /**
     * Register manually a receiver method via {@link ReceiverHandler}, based on given parameter class.
     * @param parameterClass
     * @param receiverHandler
     * @param <T>
     */
    <T>void registerReceiverMethod(final Class<T> parameterClass, final ReceiverHandler<T> receiverHandler);

    /**
     * Unregister manually a receiver method via {@link ReceiverHandler}
     * @param receiverHandler
     * @param <T>
     */
    <T>void unregisterReceiverMethod(final ReceiverHandler<T> receiverHandler);

    /**
     * Default sender method
     * @param value
     */
    @CustomEventBus.Sender
    void send(Object value);
}
