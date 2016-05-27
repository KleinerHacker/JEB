package org.pcsoft.framework.jeb.config;

/**
 * Created by pfeifchr on 26.05.2016.
 */
final class JEBPlainConfiguration implements JEBConfiguration {
    private final ThreadFactoryConfiguration threadFactoryConfiguration;
    private final ThreadRunnerConfiguration threadRunnerConfiguration;
    private final BusConfiguration busConfiguration;
    private final SurroundConfiguration surroundConfiguration;

    public JEBPlainConfiguration(BusConfiguration busConfiguration, ThreadFactoryConfiguration threadFactoryConfiguration, ThreadRunnerConfiguration threadRunnerConfiguration,
                                 SurroundConfiguration surroundConfiguration) {
        this.busConfiguration = busConfiguration;
        this.threadFactoryConfiguration = threadFactoryConfiguration;
        this.threadRunnerConfiguration = threadRunnerConfiguration;
        this.surroundConfiguration = surroundConfiguration;
    }

    @Override
    public BusConfiguration getBusConfiguration() {
        return busConfiguration;
    }

    @Override
    public ThreadFactoryConfiguration getThreadFactoryConfiguration() {
        return threadFactoryConfiguration;
    }

    @Override
    public ThreadRunnerConfiguration getThreadRunnerConfiguration() {
        return threadRunnerConfiguration;
    }

    @Override
    public SurroundConfiguration getSurroundConfiguration() {
        return surroundConfiguration;
    }

    @Override
    public String toString() {
        return "JEBPlainConfiguration{" +
                "busConfiguration=" + busConfiguration +
                ", threadFactoryConfiguration=" + threadFactoryConfiguration +
                ", threadRunnerConfiguration=" + threadRunnerConfiguration +
                ", surroundConfiguration=" + surroundConfiguration +
                '}';
    }
}
