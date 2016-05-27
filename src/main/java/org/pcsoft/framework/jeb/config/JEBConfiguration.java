package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.EventBus;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThread;
import org.pcsoft.framework.jeb.annotation.handler.SurroundAction;
import org.pcsoft.framework.jeb.type.ThreadPoolType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public interface JEBConfiguration {
    public static final class ThreadFactoryConfiguration {
        private final ThreadPoolType threadPoolType;
        private final int maxThreadCount;
        private final boolean processorCores;

        ThreadFactoryConfiguration(int maxThreadCount, ThreadPoolType threadPoolType, boolean processorCores) {
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

        @Override
        public String toString() {
            return "ThreadConfiguration{" +
                    "maxThreadCount=" + maxThreadCount +
                    ", threadPoolType=" + threadPoolType +
                    ", processorCores=" + processorCores +
                    '}';
        }
    }

    public static final class ThreadRunnerConfiguration {
        private final List<Class<? extends RunOnThread>> threadRunnerClassList = new ArrayList<>();

        ThreadRunnerConfiguration(final Collection<Class<? extends RunOnThread>> collection) {
            threadRunnerClassList.addAll(collection);
        }

        public List<Class<? extends RunOnThread>> getThreadRunnerClassList() {
            return Collections.unmodifiableList(threadRunnerClassList);
        }

        @Override
        public String toString() {
            return "ThreadRunnerConfiguration{" +
                    "threadRunnerClassList=" + threadRunnerClassList +
                    '}';
        }
    }

    public static final class BusConfiguration {
        private final Class<? extends EventBus> eventBusClass;

        BusConfiguration(Class<? extends EventBus> eventBusClass) {
            this.eventBusClass = eventBusClass;
        }

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

    public static final class SurroundConfiguration {
        private final List<Class<? extends SurroundAction>> surroundActionClassList = new ArrayList<>();

        SurroundConfiguration(final Collection<Class<? extends SurroundAction>> collection)  {
            surroundActionClassList.addAll(collection);
        }

        public List<Class<? extends SurroundAction>> getSurroundActionClassList() {
            return surroundActionClassList;
        }

        @Override
        public String toString() {
            return "SurroundConfiguration{" +
                    "surroundActionClassList=" + surroundActionClassList +
                    '}';
        }
    }

    ThreadFactoryConfiguration getThreadFactoryConfiguration();
    ThreadRunnerConfiguration getThreadRunnerConfiguration();
    BusConfiguration getBusConfiguration();
    SurroundConfiguration getSurroundConfiguration();
}
