package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadMyThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThread2;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadMyThread2;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnMyThreadWithValue;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadWithValueThread1;
import org.pcsoft.framework.jeb.qualifier.threading.RunOnThreadWithValueThread2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public class MethodBasedThreadingTest {
    private final Thread currentThread = Thread.currentThread();
    private final JEBConfiguration configuration = JEBConfigurationFactory.createDefaultBuilder()
            .withAdditionalThreadRunner(RunOnThreadMyThread1.class)
            .withAdditionalThreadRunner(RunOnThreadMyThread2.class)
            .withAdditionalThreadRunner(RunOnThreadWithValueThread1.class)
            .withAdditionalThreadRunner(RunOnThreadWithValueThread2.class)
            .build();
    private final EventBus eventBus = EventBusManager.create(configuration).getEventBus();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Test
    public void test() throws InterruptedException {
        eventBus.registerReceiverClass(this);

        eventBus.send("test");
        Thread.sleep(500);

        Assert.assertEquals(5, counter.get());
    }

    @EventReceiver
    private void onDefaultThread(final String value) {
        Assert.assertEquals(currentThread, Thread.currentThread());
        counter.incrementAndGet();
    }

    @RunOnMyThread1
    @EventReceiver
    private void onMyThread1(final String value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnThreadMyThread1.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

    @RunOnMyThread2
    @EventReceiver
    private void onMyThread2(final String value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnThreadMyThread2.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

    @RunOnMyThreadWithValue(identifier = 1)
    @EventReceiver
    private void onMyThreadWithValue1(final String value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnThreadWithValueThread1.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

    @RunOnMyThreadWithValue(identifier = 2)
    @EventReceiver
    private void onMyThreadWithValue2(final String value) {
        Assert.assertNotSame(currentThread, Thread.currentThread());
        Assert.assertEquals(RunOnThreadWithValueThread2.THREAD_NAME, Thread.currentThread().getName());
        counter.incrementAndGet();
    }

}
