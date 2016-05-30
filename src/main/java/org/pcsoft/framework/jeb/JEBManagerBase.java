package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

/**
 * Abstract base implementation for all JEB-Manager
 */
public abstract class JEBManagerBase implements JEBManager {
    protected final JEBConfiguration configuration;

    public JEBManagerBase(JEBConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final JEBConfiguration getConfiguration() {
        return configuration;
    }
}
