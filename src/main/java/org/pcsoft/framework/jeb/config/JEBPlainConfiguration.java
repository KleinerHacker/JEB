package org.pcsoft.framework.jeb.config;

/**
 * Created by pfeifchr on 26.05.2016.
 */
final class JEBPlainConfiguration implements JEBConfiguration {
    private final ThreadConfiguration threadConfiguration;
    private final BusConfiguration busConfiguration;

    public JEBPlainConfiguration(BusConfiguration busConfiguration, ThreadConfiguration threadConfiguration) {
        this.busConfiguration = busConfiguration;
        this.threadConfiguration = threadConfiguration;
    }

    @Override
    public BusConfiguration getBusConfiguration() {
        return busConfiguration;
    }

    @Override
    public ThreadConfiguration getThreadConfiguration() {
        return threadConfiguration;
    }
}
