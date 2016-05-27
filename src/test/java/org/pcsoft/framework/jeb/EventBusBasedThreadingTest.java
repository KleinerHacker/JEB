package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadMyThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread2;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadMyThread2;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadWithValueThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadWithValueThread2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pfeifchr on 27.05.2016.
 */
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
            .withAdditionalThreadRunner(RunOnThreadMyThread1.class)
            .withAdditionalThreadRunner(RunOnThreadMyThread2.class)
            .withAdditionalThreadRunner(RunOnThreadWithValueThread1.class)
            .withAdditionalThreadRunner(RunOnThreadWithValueThread2.class)
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
        Assert.assertEquals(RunOnThreadMyThread1.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

    @EventReceiver
    private void onThread2(final Integer value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnThreadMyThread2.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

}
