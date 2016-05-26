package org.pcsoft.framework.jeb.type;

import org.pcsoft.jeb.config.bus.XThreadRunner;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public enum ThreadRunner {
    CurrentThread(XThreadRunner.CURRENT_THREAD),
    NewThread(XThreadRunner.NEW_THREAD),
    ;

    public static ThreadRunner from(final XThreadRunner nativeValue) {
        for (final ThreadRunner threadRunner : values()) {
            if (threadRunner.getNativeValue() == nativeValue)
                return threadRunner;
        }

        throw new RuntimeException();
    }

    private final XThreadRunner nativeValue;

    private ThreadRunner(XThreadRunner nativeValue) {
        this.nativeValue = nativeValue;
    }

    public XThreadRunner getNativeValue() {
        return nativeValue;
    }
}
