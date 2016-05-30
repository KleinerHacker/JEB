package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread1Handler;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread2;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread2Handler;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadWithValueHandler1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadWithValueHandler2;

import java.util.concurrent.atomic.AtomicInteger;

public class EventBusBasedThreadingTest {
    @RunOnMyThread1
    @CustomEventBus
    private interface MyEventBus extends EventBus {
        @CustomEventBus.Sender
        void sendOnThread1(final String value);

        @RunOnMyThread2
        @CustomEventBus.Sender
        void sendOnThread2(final Integer value);
    }

    private final Thread currentThread = Thread.currentThread();
    private final JEBConfiguration configuration = JEBConfigurationFactory.createDefaultBuilder()
            .withAdditionalRunOnThreadHandler(RunOnMyThread1Handler.class)
            .withAdditionalRunOnThreadHandler(RunOnMyThread2Handler.class)
            .withAdditionalRunOnThreadHandler(RunOnThreadWithValueHandler1.class)
            .withAdditionalRunOnThreadHandler(RunOnThreadWithValueHandler2.class)
            .build();
    private final MyEventBus eventBus = EventBusManager.create(configuration).getEventBus(MyEventBus.class);
    private final AtomicInteger counter = new AtomicInteger(0);

    @Test
    public void test() throws InterruptedException {
        eventBus.registerReceiverClass(this);

        eventBus.send(true);
        eventBus.sendOnThread1("test");
        eventBus.sendOnThread2(123);

        Thread.sleep(500);

        Assert.assertEquals(2, counter.get());
    }

    @EventReceiver
    private void onThread1(final String value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnMyThread1Handler.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

    @EventReceiver
    private void onThread2(final Integer value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnMyThread2Handler.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

}
