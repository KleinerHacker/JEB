package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;

/**
 * This class represent the abstract base implementation for a Run-On-Thread Handler. For more infromation see
 * {@link RunOnThreadHandler} or {@link org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier}.
 */
public abstract class RunOnThreadHandlerBase<T extends Annotation> implements RunOnThreadHandler<T> {
    protected JEBConfiguration configuration;

    @Override
    public final JEBConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public final void setConfiguration(JEBConfiguration configuration) {
        this.configuration = configuration;
    }
}
