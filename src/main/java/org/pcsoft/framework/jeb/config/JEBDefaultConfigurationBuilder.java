package org.pcsoft.framework.jeb.config;

/**
 * Default Builder
 */
class JEBDefaultConfigurationBuilder extends JEBConfigurationBuilder {
    @Override
    public final JEBConfiguration build() {
        return new JEBPlainConfiguration(
                new JEBConfiguration.BusConfiguration(eventBusClass),
                new JEBConfiguration.ThreadFactoryConfiguration(fixedThreadSize, threadPoolType, processorCores),
                new JEBConfiguration.RunOnThreadConfiguration(runOnThreadHandlerClassList, scannerRunOnThread),
                new JEBConfiguration.SurroundConfiguration(surroundHandlerClassList, scannerSurround)
        );
    }
}