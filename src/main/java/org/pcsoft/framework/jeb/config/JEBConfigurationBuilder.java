package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.DefaultEventBus;
import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.type.ThreadPoolType;
import org.pcsoft.framework.jeb.type.ThreadRunner;

public abstract class JEBConfigurationBuilder {
    protected ThreadRunner threadRunner = ThreadRunner.CurrentThread;
    protected Class<? extends EventBus> eventBusClass = DefaultEventBus.class;
    protected ThreadPoolType threadPoolType = ThreadPoolType.FixedPool;
    protected int fixedThreadSize = 10;
    protected boolean processorCores = false;

    public JEBConfigurationBuilder withCachedThreadPool() {
        this.threadPoolType = ThreadPoolType.CachedPool;
        return this;
    }

    public JEBConfigurationBuilder withFixedThreadPoolCustom(final int fixedThreadSize) {
        this.threadPoolType = ThreadPoolType.FixedPool;
        this.fixedThreadSize = fixedThreadSize;
        this.processorCores = false;
        return this;
    }

    public JEBConfigurationBuilder withFixedThreadPoolProcessorCores() {
        this.threadPoolType = ThreadPoolType.FixedPool;
        this.processorCores = true;
        this.fixedThreadSize = 0;
        return this;
    }

    public JEBConfigurationBuilder withRunningOnThread(final ThreadRunner threadRunner) {
        this.threadRunner = threadRunner;
        return this;
    }

    public JEBConfigurationBuilder withDefaultEventBusClass(final Class<? extends EventBus> clazz) {
        this.eventBusClass = clazz;
        return this;
    }

    public abstract JEBConfiguration build();
}