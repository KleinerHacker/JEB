package org.pcsoft.framework.jeb.qualifier.threading;

import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandlerBase;

@RunOnMyThreadWithValue(identifier = 1)
public class RunOnThreadWithValueHandler1 extends RunOnThreadHandlerBase<RunOnMyThreadWithValue> {
    public static final String THREAD_NAME = "MyThread.WithValue.1";

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
