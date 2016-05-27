package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public interface SurroundAction<T extends Annotation> {
    void onPreInvoke(final T annotation, final Method method, final Object value);
    void onPostInvoke(final T annotation, final Method method, final Object value);

    void setConfiguration(JEBConfiguration configuration);
    JEBConfiguration getConfiguration();
}
