package org.pcsoft.framework.jeb.annotation.handler;

import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.framework.jeb.annotation.SurroundTimeLogger;

import java.lang.reflect.Method;

/**
 * Created by pfeifchr on 27.05.2016.
 */
@SurroundTimeLogger
public final class SurroundActionTimeLogger extends SurroundActionLogger<SurroundTimeLogger> {
    private final StopWatch stopWatch = new StopWatch();

    @Override
    public void onPostInvoke(SurroundTimeLogger annotation, Method method, Object value) {
        final String message = buildMessage(annotation.printType(), method, "START");
        logOut(annotation.loggingLevel(), message);

        stopWatch.reset();
        stopWatch.start();
    }

    @Override
    public void onPreInvoke(SurroundTimeLogger annotation, Method method, Object value) {
        stopWatch.stop();

        final String message = buildMessage(annotation.printType(), method, "END");
        logOut(annotation.loggingLevel(), message);
    }
}
