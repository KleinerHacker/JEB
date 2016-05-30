package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;

/**
 * Interface for all Run-On-Thread Handler.<br/>
 * <br/>
 * To implement a Run-On-Thread Handler implement this interface and annotate the class with a qualified annotation.
 * This annotation must be annotated with {@link org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier} for
 * identification of this class as handler class, like classpath scanning.
 */
public interface RunOnThreadHandler<T extends Annotation> {
    public interface MethodInvokeListener {
        void onInvokeMethod();
    }

    void runOnThread(final T annotation, final MethodInvokeListener methodInvokeListener);

    void setConfiguration(JEBConfiguration configuration);
    JEBConfiguration getConfiguration();
}
