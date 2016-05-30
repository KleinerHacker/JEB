package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;

/**
 * Represent the abstract base implementation of a Surround Handler. For more information see {@link SurroundHandler} or
 * {@link org.pcsoft.framework.jeb.annotation.SurroundQualifier}.
 */
public abstract class SurroundHandlerBase<T extends Annotation> implements SurroundHandler<T> {
    private JEBConfiguration configuration;

    @Override
    public final void setConfiguration(JEBConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final JEBConfiguration getConfiguration() {
        return configuration;
    }
}
