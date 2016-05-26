package org.pcsoft.framework.jeb.type.listener;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public interface ReceiverHandler<T> {
    void onReceive(final T value);
}
