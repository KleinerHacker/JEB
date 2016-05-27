package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.type.LoggingLevel;
import org.pcsoft.framework.jeb.type.PrintType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public abstract class SurroundActionLogger<T extends Annotation> extends SurroundActionBase<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurroundActionLogger.class);

    protected final String buildMessage(PrintType printType, Method method, String prefix) {
        final String message;
        switch (printType) {
            case Method:
                message = prefix + ": " + method.getName();
                break;
            case MethodAndClass:
                message = prefix + ": " + method.getDeclaringClass().getName() + "#" + method.getName();
                break;
            default:
                throw new RuntimeException();
        }
        return message;
    }

    protected final void logOut(LoggingLevel loggingLevel, String message) {
        switch (loggingLevel) {
            case Error:
                LOGGER.error(message);
                break;
            case Warn:
                LOGGER.warn(message);
                break;
            case Info:
                LOGGER.info(message);
                break;
            case Debug:
                LOGGER.debug(message);
                break;
            case Trace:
                LOGGER.trace(message);
                break;
            default:
                throw new RuntimeException();
        }
    }
}
