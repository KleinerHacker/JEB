package org.pcsoft.framework.jeb.qualifier.threading;

import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadBase;

/**
 * Created by pfeifchr on 27.05.2016.
 */
@RunOnMyThread2
public class RunOnThreadMyThread2 extends RunOnThreadBase<RunOnMyThread2> {
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
