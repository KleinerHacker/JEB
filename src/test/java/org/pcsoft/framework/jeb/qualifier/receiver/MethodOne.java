package org.pcsoft.framework.jeb.qualifier.receiver;

import org.pcsoft.framework.jeb.annotation.EventReceiverQualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@EventReceiverQualifier
public @interface MethodOne {
}
