package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.annotation.RunOnNewThread;
import org.pcsoft.framework.jeb.type.ThreadFactory;

/**
 * Represent the builtin Run-On-Thread Handler that creates a new thread on the builtin {@link ThreadFactory}, see {@link RunOnNewThread}.
 * For configuration see {@link org.pcsoft.framework.jeb.config.JEBConfiguration.ThreadFactoryConfiguration}.
 */
@org.pcsoft.framework.jeb.annotation.RunOnNewThread
public final class RunOnThreadNewThreadHandler extends RunOnThreadHandlerBase<RunOnNewThread> {

    @Override
    public void runOnThread(final org.pcsoft.framework.jeb.annotation.RunOnNewThread annotation, final MethodInvokeListener methodInvokeListener) {
        ThreadFactory.getInstance(configuration).submit(new Runnable() {
            @Override
            public void run() {
                methodInvokeListener.onInvokeMethod();
            }
        });
    }
}
