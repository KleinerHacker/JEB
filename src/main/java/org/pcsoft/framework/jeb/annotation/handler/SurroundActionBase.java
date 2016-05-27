package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.lang.annotation.Annotation;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public abstract class SurroundActionBase<T extends Annotation> implements SurroundAction<T> {
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
