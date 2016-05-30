package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Interface for all Surround Handler.<br/>
 * <br/>
 * To implement a Surround Handler a class must implement this interface and annotate the class with a qualified annotation.
 * This annotation must be annotated with {@link org.pcsoft.framework.jeb.annotation.SurroundQualifier} for
 * identification of this class as handler class, like classpath scanning.
 */
public interface SurroundHandler<T extends Annotation> {
    void onPreInvoke(final T annotation, final Method method, final Object value);
    void onPostInvoke(final T annotation, final Method method, final Object value);

    void setConfiguration(JEBConfiguration configuration);
    JEBConfiguration getConfiguration();
}
