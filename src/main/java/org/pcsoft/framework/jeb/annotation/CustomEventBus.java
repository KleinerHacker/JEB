package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by pfeifchr on 26.05.2016.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CustomEventBus {

    @Documented
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface Sender {

    }
}
