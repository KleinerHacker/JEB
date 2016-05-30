package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;
import org.pcsoft.framework.jeb.config.xml.XJEBConfiguration;
import org.pcsoft.framework.jeb.exception.JEBConfigurationException;
import org.pcsoft.framework.jeb.type.ThreadPoolType;

/**
 * Configuration builder for XML
 */
class JEBXMLConfigurationBuilder extends JEBDefaultConfigurationBuilder {
    public JEBXMLConfigurationBuilder(XJEBConfiguration configuration) {
        if (configuration.getBus() != null) {
            try {
                this.eventBusClass = (Class<? extends EventBus>) Class.forName(configuration.getBus().getDefaultClass());
            } catch (ClassNotFoundException e) {
                throw new JEBConfigurationException("Unable to find event bus class: " + configuration.getBus().getDefaultClass(), e);
            } catch (ClassCastException e) {
                throw new JEBConfigurationException("The given event bus class is not implemented " + EventBus.class + ": " + configuration.getBus().getDefaultClass());
            }
        }
        if (configuration.getThreadFactory() != null) {
            if (configuration.getThreadFactory().getCachedThreadPool() != null) {
                this.threadPoolType = ThreadPoolType.CachedPool;
            } else if (configuration.getThreadFactory().getFixedThreadPool() != null) {
                this.threadPoolType = ThreadPoolType.FixedPool;
                this.fixedThreadSize = configuration.getThreadFactory().getFixedThreadPool().getFixedSize();
                this.processorCores = configuration.getThreadFactory().getFixedThreadPool().isProcessorCore();
            }
        }
        if (configuration.getRunOnThreadHandlers() != null) {
            for (final String runOnThreadHandlerClassString : configuration.getRunOnThreadHandlers().getRunOnThreadHandler()) {
                try {
                    this.runOnThreadHandlerClassList.add((Class<? extends RunOnThreadHandler>) Class.forName(runOnThreadHandlerClassString));
                } catch (ClassNotFoundException e) {
                    throw new JEBConfigurationException("Unable to find thread runner class: " + runOnThreadHandlerClassString, e);
                }
            }
            this.scannerRunOnThread = !configuration.getRunOnThreadHandlers().isUseClasspathScanner() ? null :
                    new JEBConfiguration.ClasspathScanningConfiguration(configuration.getRunOnThreadHandlers().getClasspathScanningPackage());
        }
        if (configuration.getSurroundHandlers() != null) {
            for (final String surroundHandlerClassString : configuration.getSurroundHandlers().getSurroundHandler()) {
                try {
                    this.surroundHandlerClassList.add((Class<? extends SurroundHandler>) Class.forName(surroundHandlerClassString));
                } catch (ClassNotFoundException e) {
                    throw new JEBConfigurationException("Unable to find surround action class: " + surroundHandlerClassString, e);
                }
            }
            this.scannerSurround = !configuration.getSurroundHandlers().isUseClasspathScanner() ? null :
                    new JEBConfiguration.ClasspathScanningConfiguration(configuration.getSurroundHandlers().getClasspathScanningPackage());
        }
    }
}
