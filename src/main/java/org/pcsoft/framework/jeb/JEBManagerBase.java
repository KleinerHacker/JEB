package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

/**
 * Created by pfeifchr on 27.05.2016.
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
