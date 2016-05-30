package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to run the receiver method on a new thread on the builtin {@link org.pcsoft.framework.jeb.type.ThreadFactory}, see
 * {@link org.pcsoft.framework.jeb.config.JEBConfiguration.ThreadFactoryConfiguration}, {@link org.pcsoft.framework.jeb.annotation.handler.RunOnThreadNewThreadHandler}.
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@RunOnThreadQualifier
public @interface RunOnNewThread {
}
