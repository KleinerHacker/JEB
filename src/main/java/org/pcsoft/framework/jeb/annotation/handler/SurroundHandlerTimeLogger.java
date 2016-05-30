package org.pcsoft.framework.jeb.annotation.handler;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.framework.jeb.annotation.SurroundTimeLogger;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represent a Surround Handler implementation to print out method running times, see {@link SurroundTimeLogger}.
 */
@SurroundTimeLogger
public final class SurroundHandlerTimeLogger extends SurroundHandlerLogger<SurroundTimeLogger> {
    private final StopWatch stopWatch = new StopWatch();

    @Override
    public void onPostInvoke(SurroundTimeLogger annotation, Method method, Object value) {
        final String message = buildMessage(annotation.printType(), method, "START");
        logOut(annotation.loggingClass(), annotation.loggingLevel(), message + " - " + new SimpleDateFormat(annotation.timeFormat()).format(new Date()));

        stopWatch.reset();
        stopWatch.start();
    }

    @Override
    public void onPreInvoke(SurroundTimeLogger annotation, Method method, Object value) {
        stopWatch.stop();

        final String message = buildMessage(annotation.printType(), method, "END");
        logOut(annotation.loggingClass(), annotation.loggingLevel(), message + "- " + new SimpleDateFormat(annotation.timeFormat()).format(new Date()));
        logOut(annotation.loggingClass(), annotation.loggingLevel(), "Time: " + DurationFormatUtils.formatDuration(stopWatch.getTime(), annotation.durationFormat()));
    }
}
