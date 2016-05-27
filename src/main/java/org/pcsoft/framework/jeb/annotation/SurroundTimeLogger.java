package org.pcsoft.framework.jeb.annotation;

import org.pcsoft.framework.jeb.type.LoggingLevel;
import org.pcsoft.framework.jeb.type.PrintType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by pfeifchr on 27.05.2016.
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@SurroundQualifier
public @interface SurroundTimeLogger {
    PrintType printType() default PrintType.MethodAndClass;
    LoggingLevel loggingLevel() default LoggingLevel.Trace;
}
