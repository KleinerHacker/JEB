package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThread;
import org.pcsoft.framework.jeb.annotation.handler.SurroundAction;
import org.pcsoft.framework.jeb.exception.JEBConfigurationException;
import org.pcsoft.framework.jeb.type.ThreadPoolType;
import org.pcsoft.jeb.config.XJEBConfiguration;

/**
 * Created by pfeifchr on 26.05.2016.
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
        if (configuration.getThreadRunners() != null) {
            for (final String threadRunnerClassString : configuration.getThreadRunners().getThreadRunner()) {
                try {
                    this.threadRunnerClassList.add((Class<? extends RunOnThread>) Class.forName(threadRunnerClassString));
                } catch (ClassNotFoundException e) {
                    throw new JEBConfigurationException("Unable to find thread runner class: " + threadRunnerClassString, e);
                }
            }
        }
        if (configuration.getSurroundActions() != null) {
            for (final String surroundActionClassString : configuration.getSurroundActions().getSurroundAction()) {
                try {
                    this.surroundActionClassList.add((Class<? extends SurroundAction>) Class.forName(surroundActionClassString));
                } catch (ClassNotFoundException e) {
                    throw new JEBConfigurationException("Unable to find surround action class: " + surroundActionClassString, e);
                }
            }
        }
    }
}
