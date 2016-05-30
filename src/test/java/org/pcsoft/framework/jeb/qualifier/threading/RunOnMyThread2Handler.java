package org.pcsoft.framework.jeb.qualifier.threading;

import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandlerBase;

@RunOnMyThread2
public class RunOnMyThread2Handler extends RunOnThreadHandlerBase<RunOnMyThread2> {
    public static final String THREAD_NAME = "MyThread.2";

    @Override
    public void runOnThread(final RunOnMyThread2 annotation, final MethodInvokeListener methodInvokeListener) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                methodInvokeListener.onInvokeMethod();
            }
        }, THREAD_NAME);
        thread.setDaemon(true);
        thread.start();
    }
}
