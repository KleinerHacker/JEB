package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.type.ThreadPoolType;
import org.pcsoft.framework.jeb.type.ThreadRunner;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public interface JEBConfiguration {
    public static final class ThreadConfiguration {
        private final ThreadPoolType threadPoolType;
        private final int maxThreadCount;
        private final boolean processorCores;

        ThreadConfiguration(int maxThreadCount, ThreadPoolType threadPoolType, boolean processorCores) {
            this.maxThreadCount = maxThreadCount;
            this.threadPoolType = threadPoolType;
            this.processorCores = processorCores;
        }

        public int getMaxThreadCount() {
            return maxThreadCount;
        }

        public boolean isProcessorCores() {
            return processorCores;
        }

        public ThreadPoolType getThreadPoolType() {
            return threadPoolType;
        }
    }

    public static final class BusConfiguration {
        private final ThreadRunner threadRunner;
        private final Class<? extends EventBus> eventBusClass;

        BusConfiguration(Class<? extends EventBus> eventBusClass, ThreadRunner threadRunner) {
            this.eventBusClass = eventBusClass;
            this.threadRunner = threadRunner;
        }

        public ThreadRunner getThreadRunner() {
            return threadRunner;
        }

        public Class<? extends EventBus> getEventBusClass() {
            return eventBusClass;
        }
    }

    ThreadConfiguration getThreadConfiguration();
    BusConfiguration getBusConfiguration();
}
