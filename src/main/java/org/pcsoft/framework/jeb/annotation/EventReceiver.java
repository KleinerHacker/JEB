package org.pcsoft.framework.jeb.annotation;

import org.pcsoft.framework.jeb.type.ThreadRunner;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by pfeifchr on 26.05.2016.
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface EventReceiver {
    ThreadRunner runOnThread() default ThreadRunner.CurrentThread;
}
