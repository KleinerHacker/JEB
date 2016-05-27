package org.pcsoft.framework.jeb.config;

class JEBDefaultConfigurationBuilder extends JEBConfigurationBuilder {
    @Override
    public final JEBConfiguration build() {
        return new JEBPlainConfiguration(
                new JEBConfiguration.BusConfiguration(eventBusClass),
                new JEBConfiguration.ThreadFactoryConfiguration(fixedThreadSize, threadPoolType, processorCores),
                new JEBConfiguration.ThreadRunnerConfiguration(threadRunnerClassList),
                new JEBConfiguration.SurroundConfiguration(surroundActionClassList)
        );
    }
}