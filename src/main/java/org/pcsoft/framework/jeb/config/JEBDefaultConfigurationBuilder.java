package org.pcsoft.framework.jeb.config;

class JEBDefaultConfigurationBuilder extends JEBConfigurationBuilder {
    @Override
    public final JEBConfiguration build() {
        return new JEBPlainConfiguration(
                new JEBConfiguration.BusConfiguration(eventBusClass, threadRunner),
                new JEBConfiguration.ThreadConfiguration(fixedThreadSize, threadPoolType, processorCores)
        );
    }
}