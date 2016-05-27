package org.pcsoft.framework.jeb.annotation.handler;

import org.pcsoft.framework.jeb.annotation.RunOnNewThread;
import org.pcsoft.framework.jeb.type.ThreadFactory;

/**
 * Created by pfeifchr on 27.05.2016.
 */
@org.pcsoft.framework.jeb.annotation.RunOnNewThread
public final class RunOnThreadNewThread extends RunOnThreadBase<org.pcsoft.framework.jeb.annotation.RunOnNewThread> {

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
