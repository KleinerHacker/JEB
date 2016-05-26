package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.type.ThreadPoolType;
import org.pcsoft.framework.jeb.type.ThreadRunner;
import org.pcsoft.jeb.config.XJEBConfiguration;

/**
 * Created by pfeifchr on 26.05.2016.
 */
class JEBXMLConfigurationBuilder extends JEBDefaultConfigurationBuilder {
    public JEBXMLConfigurationBuilder(XJEBConfiguration configuration) {
        if (configuration.getBus() != null) {
            this.threadRunner = ThreadRunner.from(configuration.getBus().getThreadRunner());
            try {
                this.eventBusClass = (Class<? extends EventBus>) Class.forName(configuration.getBus().getDefaultClass());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Unable to find event bus class: " + configuration.getBus().getDefaultClass(), e);
            } catch (ClassCastException e) {
                throw new IllegalStateException("The given event bus class is not implemented " + EventBus.class + ": " + configuration.getBus().getDefaultClass());
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
    }
}
