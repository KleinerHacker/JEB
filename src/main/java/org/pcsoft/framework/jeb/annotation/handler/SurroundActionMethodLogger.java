package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.annotation.SurroundMethodLogger;

import java.lang.reflect.Method;

/**
 * Created by pfeifchr on 27.05.2016.
 */
@SurroundMethodLogger
public final class SurroundActionMethodLogger extends SurroundActionLogger<SurroundMethodLogger> {

    @Override
    public void onPostInvoke(SurroundMethodLogger annotation, Method method, Object value) {

    }

    @Override
    public void onPreInvoke(SurroundMethodLogger annotation, Method method, Object value) {

    }
}
