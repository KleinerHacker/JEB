package org.pcsoft.framework.jeb.annotation;

import org.pcsoft.framework.jeb.annotation.handler.SurroundHandlerMethodLogger;
import org.pcsoft.framework.jeb.type.LoggingLevel;
import org.pcsoft.framework.jeb.type.PrintType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to use simple method logging surround handler to log out the method entry and exit moments.
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@SurroundQualifier
public @interface SurroundMethodLogger {
    PrintType printType() default PrintType.MethodAndClass;
    LoggingLevel loggingLevel() default LoggingLevel.Trace;
    Class loggingClass() default SurroundHandlerMethodLogger.class;
}
