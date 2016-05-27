package org.pcsoft.framework.jeb.qualifier.threading;

import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadBase;

/**
 * Created by pfeifchr on 27.05.2016.
 */
@RunOnMyThreadWithValue(identifier = 2)
public class RunOnThreadWithValueThread2 extends RunOnThreadBase<RunOnMyThreadWithValue> {
    public static final String THREAD_NAME = "MyThread.WithValue.2";

    @Override
    public void runOnThread(final RunOnMyThreadWithValue annotation, final MethodInvokeListener methodInvokeListener) {
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
