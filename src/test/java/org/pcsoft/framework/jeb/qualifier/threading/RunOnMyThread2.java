package org.pcsoft.framework.jeb.qualifier.threading;

import org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@RunOnThreadQualifier
public @interface RunOnMyThread2 {
}
