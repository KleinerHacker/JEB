package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public interface RunOnThread<T extends Annotation> {
    public interface MethodInvokeListener {
        void onInvokeMethod();
    }

    void runOnThread(final T annotation, final MethodInvokeListener methodInvokeListener);

    void setConfiguration(JEBConfiguration configuration);
    JEBConfiguration getConfiguration();
}
