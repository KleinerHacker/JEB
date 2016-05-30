package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.annotation.SurroundMethodLogger;

import java.lang.reflect.Method;

/**
 * Represent a Surround Handler implementation for a simple method entry and exit logging, see {@link SurroundMethodLogger}.
 */
@SurroundMethodLogger
public final class SurroundHandlerMethodLogger extends SurroundHandlerLogger<SurroundMethodLogger> {

    @Override
    public void onPostInvoke(SurroundMethodLogger annotation, Method method, Object value) {
        logOut(annotation.loggingClass(), annotation.loggingLevel(), buildMessage(annotation.printType(), method, "START"));
    }

    @Override
    public void onPreInvoke(SurroundMethodLogger annotation, Method method, Object value) {
        logOut(annotation.loggingClass(), annotation.loggingLevel(), buildMessage(annotation.printType(), method, "END"));
    }
}
