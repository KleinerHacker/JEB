package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.DefaultEventBus;
import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThread;
import org.pcsoft.framework.jeb.annotation.handler.SurroundAction;
import org.pcsoft.framework.jeb.type.ThreadPoolType;

import java.util.ArrayList;
import java.util.List;

public abstract class JEBConfigurationBuilder {
    protected Class<? extends EventBus> eventBusClass = DefaultEventBus.class;
    protected ThreadPoolType threadPoolType = ThreadPoolType.FixedPool;
    protected int fixedThreadSize = 10;
    protected boolean processorCores = false;
    protected final List<Class<? extends RunOnThread>> threadRunnerClassList = new ArrayList<>();
    protected final List<Class<? extends SurroundAction>> surroundActionClassList = new ArrayList<>();

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

    public JEBConfigurationBuilder withDefaultEventBusClass(final Class<? extends EventBus> clazz) {
        this.eventBusClass = clazz;
        return this;
    }

    public JEBConfigurationBuilder withAdditionalThreadRunner(final Class<? extends RunOnThread> threadRunnerClass) {
        this.threadRunnerClassList.add(threadRunnerClass);
        return this;
    }

    public JEBConfigurationBuilder withAdditionalSurroundAction(final Class<? extends SurroundAction> surroundActionClass) {
        this.surroundActionClassList.add(surroundActionClass);
        return this;
    }

    public abstract JEBConfiguration build();
}