package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.qualifier.receiver.MethodOne;
import org.pcsoft.framework.jeb.qualifier.receiver.MethodTwo;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ComplexAnnotationTest {
    @CustomEventBus
    public interface MyEventBus extends EventBus {
        @MethodOne
        @CustomEventBus.Sender
        void sendToMethodOne(final Object value);

        @MethodTwo
        @CustomEventBus.Sender
        void sendToMethodTwo(final Object value);

        @MethodOne
        @MethodTwo
        @CustomEventBus.Sender
        void sendToAll(final Object value);
    }

    private MyEventBus eventBus = EventBusManager.create().getEventBus(MyEventBus.class);
    private final AtomicInteger simpleCounter = new AtomicInteger(0), oneCounter = new AtomicInteger(0), twoCounter = new AtomicInteger(0),
            allCounter = new AtomicInteger(0);

    @Test
    public void test() {
        eventBus.registerReceiverClass(this);

        eventBus.registerReceiverMethod(String.class, new ReceiverHandler<String>() {
            @MethodOne
            @MethodTwo
            @Override
            public void onReceive(String value) {
                allCounter.incrementAndGet();
            }
        });

        eventBus.send("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(0, oneCounter.get());
        Assert.assertEquals(0, twoCounter.get());
        Assert.assertEquals(0, allCounter.get());

        eventBus.sendToMethodOne("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(1, oneCounter.get());
        Assert.assertEquals(0, twoCounter.get());
        Assert.assertEquals(0, allCounter.get());

        eventBus.sendToMethodTwo("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(1, oneCounter.get());
        Assert.assertEquals(1, twoCounter.get());
        Assert.assertEquals(0, allCounter.get());

        eventBus.sendToAll("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(1, oneCounter.get());
        Assert.assertEquals(1, twoCounter.get());
        Assert.assertEquals(1, allCounter.get());
    }

    @EventReceiver
    private void receiveSimple(final String value) {
        simpleCounter.incrementAndGet();
    }

    @MethodOne
    @EventReceiver
    private void receiveOne(final String value) {
        oneCounter.incrementAndGet();
    }

    @MethodTwo
    @EventReceiver
    private void receiveTwo(final String value) {
        twoCounter.incrementAndGet();
    }
}
