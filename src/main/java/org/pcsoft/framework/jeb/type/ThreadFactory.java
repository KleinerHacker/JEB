package org.pcsoft.framework.jeb.type;

import org.pcsoft.framework.jeb.config.JEBConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class ThreadFactory {
    private static ThreadFactory INSTANCE;

    public static ThreadFactory getInstance(JEBConfiguration configuration) {
        if (INSTANCE == null) {
            switch (configuration.getThreadFactoryConfiguration().getThreadPoolType()) {
                case CachedPool:
                    INSTANCE = new ThreadFactory(Executors.newCachedThreadPool());
                    break;
                case FixedPool:
                    INSTANCE = new ThreadFactory(Executors.newFixedThreadPool(
                            configuration.getThreadFactoryConfiguration().isProcessorCores() ?
                                    Runtime.getRuntime().availableProcessors() :
                                    configuration.getThreadFactoryConfiguration().getMaxThreadCount()
                    ));
                    break;
                default:
                    throw new RuntimeException();
            }
        }

        return INSTANCE;
    }

    private final ExecutorService executorService;

    private ThreadFactory(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void submit(Runnable runnable) {
        executorService.submit(runnable);
    }
}
