package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;
import org.pcsoft.framework.jeb.type.ThreadPoolType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represent the configuration interface for the JEB framework
 */
public interface JEBConfiguration {
    /**
     * Contains settings for classpath scanning
     */
    public static final class ClasspathScanningConfiguration {
        private final String packageRestriction;

        public ClasspathScanningConfiguration() {
            this(null);
        }

        public ClasspathScanningConfiguration(String packageRestriction) {
            this.packageRestriction = packageRestriction;
        }

        public String getPackageRestriction() {
            return packageRestriction;
        }

        @Override
        public String toString() {
            return "ClasspathScanningConfiguration{" +
                    "packageRestriction='" + packageRestriction + '\'' +
                    '}';
        }
    }

    /**
     * Contains settings for builtin thread factory, see {@link org.pcsoft.framework.jeb.type.ThreadFactory}, {@link org.pcsoft.framework.jeb.annotation.RunOnNewThread},
     * {@link org.pcsoft.framework.jeb.annotation.handler.RunOnThreadNewThreadHandler}
     */
    public static final class ThreadFactoryConfiguration {
        private final ThreadPoolType threadPoolType;
        private final int maxThreadCount;
        private final boolean processorCores;

        ThreadFactoryConfiguration(int maxThreadCount, ThreadPoolType threadPoolType, boolean processorCores) {
            this.maxThreadCount = maxThreadCount;
            this.threadPoolType = threadPoolType;
            this.processorCores = processorCores;
        }

        /**
         * Max count of threads in case of a fixed thread pool and {@link #isProcessorCores()} is <code>false</code>.
         * @return
         */
        public int getMaxThreadCount() {
            return maxThreadCount;
        }

        /**
         * TRUE if count of processor cores of machine is used (fixed thread pool only), otherwise FALSE, see {@link #getMaxThreadCount()}
         * @return
         */
        public boolean isProcessorCores() {
            return processorCores;
        }

        /**
         * Returns the configured {@link ThreadPoolType}
         * @return
         */
        public ThreadPoolType getThreadPoolType() {
            return threadPoolType;
        }

        @Override
        public String toString() {
            return "ThreadConfiguration{" +
                    "maxThreadCount=" + maxThreadCount +
                    ", threadPoolType=" + threadPoolType +
                    ", processorCores=" + processorCores +
                    '}';
        }
    }

    /**
     * Configuration for run on thread system
     */
    public static final class RunOnThreadConfiguration {
        private final List<Class<? extends RunOnThreadHandler>> runOnThreadHandlerClassList = new ArrayList<>();
        private final ClasspathScanningConfiguration classpathScanningConfiguration;

        RunOnThreadConfiguration(final Collection<Class<? extends RunOnThreadHandler>> collection, ClasspathScanningConfiguration classpathScanningConfiguration) {
            runOnThreadHandlerClassList.addAll(collection);
            this.classpathScanningConfiguration = classpathScanningConfiguration;
        }

        /**
         * Run-On-Thread Handler classes
         * @return
         */
        public List<Class<? extends RunOnThreadHandler>> getRunOnThreadHandlerClassList() {
            return Collections.unmodifiableList(runOnThreadHandlerClassList);
        }

        /**
         * Classpath scanning settings, can be NULL
         * @return
         */
        public ClasspathScanningConfiguration getClasspathScanningConfiguration() {
            return classpathScanningConfiguration;
        }

        @Override
        public String toString() {
            return "RunOnThreadConfiguration{" +
                    "classpathScanningConfiguration=" + classpathScanningConfiguration +
                    ", runOnThreadHandlerClassList=" + runOnThreadHandlerClassList +
                    '}';
        }
    }

    /**
     * Configuration for BUS
     */
    public static final class BusConfiguration {
        private final Class<? extends EventBus> eventBusClass;

        BusConfiguration(Class<? extends EventBus> eventBusClass) {
            this.eventBusClass = eventBusClass;
        }

        /**
         * Returns the default event bus class
         * @return
         */
        public Class<? extends EventBus> getEventBusClass() {
            return eventBusClass;
        }

        @Override
        public String toString() {
            return "BusConfiguration{" +
                    "eventBusClass=" + eventBusClass +
                    '}';
        }
    }

    /**
     * Configuration for surround system
     */
    public static final class SurroundConfiguration {
        private final List<Class<? extends SurroundHandler>> surroundHandlerClassList = new ArrayList<>();
        private final ClasspathScanningConfiguration classpathScanningConfiguration;

        SurroundConfiguration(final Collection<Class<? extends SurroundHandler>> collection, ClasspathScanningConfiguration classpathScanningConfiguration)  {
            surroundHandlerClassList.addAll(collection);
            this.classpathScanningConfiguration = classpathScanningConfiguration;
        }

        /**
         * Surround handler classes
         * @return
         */
        public List<Class<? extends SurroundHandler>> getSurroundHandlerClassList() {
            return surroundHandlerClassList;
        }

        /**
         * Classpath scanning settings, can be NULL
         * @return
         */
        public ClasspathScanningConfiguration getClasspathScanningConfiguration() {
            return classpathScanningConfiguration;
        }

        @Override
        public String toString() {
            return "SurroundConfiguration{" +
                    "classpathScanningConfiguration=" + classpathScanningConfiguration +
                    ", surroundHandlerClassList=" + surroundHandlerClassList +
                    '}';
        }
    }

    /**
     * Returns settings for builtin {@link org.pcsoft.framework.jeb.type.ThreadFactory}
     * @return
     */
    ThreadFactoryConfiguration getThreadFactoryConfiguration();

    /**
     * Returns settings for Run-On-Thread System
     * @return
     */
    RunOnThreadConfiguration getRunOnThreadConfiguration();

    /**
     * Returns settings for BUS
     * @return
     */
    BusConfiguration getBusConfiguration();

    /**
     * Returns settings for surround system
     * @return
     */
    SurroundConfiguration getSurroundConfiguration();
}
