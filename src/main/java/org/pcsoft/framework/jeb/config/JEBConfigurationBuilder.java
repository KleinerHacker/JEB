package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.DefaultEventBus;
import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.EventBusManager;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;
import org.pcsoft.framework.jeb.type.ThreadPoolType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the abstract base for a configuration builder, see {@link JEBConfigurationFactory}.
 */
public abstract class JEBConfigurationBuilder {
    protected Class<? extends EventBus> eventBusClass = DefaultEventBus.class;
    protected ThreadPoolType threadPoolType = ThreadPoolType.FixedPool;
    protected int fixedThreadSize = 10;
    protected boolean processorCores = false;
    protected final List<Class<? extends RunOnThreadHandler>> runOnThreadHandlerClassList = new ArrayList<>();
    protected final List<Class<? extends SurroundHandler>> surroundHandlerClassList = new ArrayList<>();
    protected JEBConfiguration.ClasspathScanningConfiguration scannerRunOnThread = null, scannerSurround = null;

    /**
     * Configuration for a cached thread pool for builtin {@link org.pcsoft.framework.jeb.type.ThreadFactory}, see {@link ThreadPoolType}
     * @return
     */
    public JEBConfigurationBuilder withCachedThreadPool() {
        this.threadPoolType = ThreadPoolType.CachedPool;
        return this;
    }

    /**
     * Configuration for a fixed thread pool for builtin {@link org.pcsoft.framework.jeb.type.ThreadFactory} with custom size, see {@link ThreadPoolType}
     * @param fixedThreadSize
     * @return
     */
    public JEBConfigurationBuilder withFixedThreadPoolCustom(final int fixedThreadSize) {
        this.threadPoolType = ThreadPoolType.FixedPool;
        this.fixedThreadSize = fixedThreadSize;
        this.processorCores = false;
        return this;
    }

    /**
     * Configuration for a fixed thread pool for builtin {@link org.pcsoft.framework.jeb.type.ThreadFactory} with machine core size, see {@link ThreadPoolType}
     * @return
     */
    public JEBConfigurationBuilder withFixedThreadPoolProcessorCores() {
        this.threadPoolType = ThreadPoolType.FixedPool;
        this.processorCores = true;
        this.fixedThreadSize = 0;
        return this;
    }

    /**
     * Setup the default event bus class, used with {@link EventBusManager#getEventBus()}
     * @param clazz
     * @return
     */
    public JEBConfigurationBuilder withDefaultEventBusClass(final Class<? extends EventBus> clazz) {
        this.eventBusClass = clazz;
        return this;
    }

    /**
     * Register a new Run-On-Thread handler class
     * @param RunOnThreadHandlerClass
     * @return
     */
    public JEBConfigurationBuilder withAdditionalRunOnThreadHandler(final Class<? extends RunOnThreadHandler> RunOnThreadHandlerClass) {
        this.runOnThreadHandlerClassList.add(RunOnThreadHandlerClass);
        return this;
    }

    /**
     * Register a new surround handler class
     * @param surroundHandlerClass
     * @return
     */
    public JEBConfigurationBuilder withAdditionalSurroundHandler(final Class<? extends SurroundHandler> surroundHandlerClass) {
        this.surroundHandlerClassList.add(surroundHandlerClass);
        return this;
    }

    /**
     * Setup classpath scanning for surround handler
     * @param scannerSurround
     * @return
     */
    public JEBConfigurationBuilder withClasspathScanningForSurroundHandler(final JEBConfiguration.ClasspathScanningConfiguration scannerSurround) {
        this.scannerSurround = scannerSurround;
        return this;
    }

    /**
     * Setup classpath scanning for Run-On-Thread handler
     * @param scannerRunOnThread
     * @return
     */
    public JEBConfigurationBuilder withClasspathScanningForRunOnThreadHandler(final JEBConfiguration.ClasspathScanningConfiguration scannerRunOnThread) {
        this.scannerRunOnThread = scannerRunOnThread;
        return this;
    }

    /**
     * Setup classpath scanning for all scanner jobs, see {@link #withClasspathScanningForRunOnThreadHandler(JEBConfiguration.ClasspathScanningConfiguration)},
     * {@link #withClasspathScanningForSurroundHandler(JEBConfiguration.ClasspathScanningConfiguration)}
     * @param scannerAll
     * @return
     */
    public JEBConfigurationBuilder withClasspathScannerForAll(final JEBConfiguration.ClasspathScanningConfiguration scannerAll) {
        return this
                .withClasspathScanningForSurroundHandler(scannerAll)
                .withClasspathScanningForRunOnThreadHandler(scannerAll);
    }

    /**
     * Build the configuration
     * @return
     */
    public abstract JEBConfiguration build();
}