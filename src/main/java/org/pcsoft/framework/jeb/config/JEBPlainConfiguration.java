package org.pcsoft.framework.jeb.config;

/**
 * Plain JEB configuration implementation
 */
final class JEBPlainConfiguration implements JEBConfiguration {
    private final ThreadFactoryConfiguration threadFactoryConfiguration;
    private final RunOnThreadConfiguration runOnThreadConfiguration;
    private final BusConfiguration busConfiguration;
    private final SurroundConfiguration surroundConfiguration;

    public JEBPlainConfiguration(BusConfiguration busConfiguration, ThreadFactoryConfiguration threadFactoryConfiguration, RunOnThreadConfiguration runOnThreadConfiguration,
                                 SurroundConfiguration surroundConfiguration) {
        this.busConfiguration = busConfiguration;
        this.threadFactoryConfiguration = threadFactoryConfiguration;
        this.runOnThreadConfiguration = runOnThreadConfiguration;
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

    public RunOnThreadConfiguration getRunOnThreadConfiguration() {
        return runOnThreadConfiguration;
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
                ", threadRunnerConfiguration=" + runOnThreadConfiguration +
                ", surroundConfiguration=" + surroundConfiguration +
                '}';
    }
}
