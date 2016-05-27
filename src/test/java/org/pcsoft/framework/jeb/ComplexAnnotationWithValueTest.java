package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.qualifier.receiver.MethodWithValue;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public class ComplexAnnotationWithValueTest {
    @CustomEventBus
    public interface MyEventBus extends EventBus {
        @MethodWithValue(identifier = 1)
        @CustomEventBus.Sender
        void sendToMethodOne(final Object value);

        @MethodWithValue(identifier = 2)
        @CustomEventBus.Sender
        void sendToMethodTwo(final Object value);

        @MethodWithValue(identifier = 3)
        @CustomEventBus.Sender
        void sendToMethodThree(final Object value);
    }

    private MyEventBus eventBus = EventBusManager.create().getEventBus(MyEventBus.class);
    private final AtomicInteger simpleCounter = new AtomicInteger(0), oneCounter = new AtomicInteger(0), twoCounter = new AtomicInteger(0), threeCounter = new AtomicInteger(0);

    @Test
    public void test() {
        eventBus.registerReceiverClass(this);

        eventBus.registerReceiverMethod(String.class, new ReceiverHandler<String>() {
            @MethodWithValue(identifier = 3)
            @Override
            public void onReceive(String value) {
                threeCounter.incrementAndGet();
            }
        });

        eventBus.send("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(0, oneCounter.get());
        Assert.assertEquals(0, twoCounter.get());
        Assert.assertEquals(0, threeCounter.get());

        eventBus.sendToMethodOne("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(1, oneCounter.get());
        Assert.assertEquals(0, twoCounter.get());
        Assert.assertEquals(0, threeCounter.get());

        eventBus.sendToMethodTwo("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(1, oneCounter.get());
        Assert.assertEquals(1, twoCounter.get());
        Assert.assertEquals(0, threeCounter.get());

        eventBus.sendToMethodThree("test");
        Assert.assertEquals(1, simpleCounter.get());
        Assert.assertEquals(1, oneCounter.get());
        Assert.assertEquals(1, twoCounter.get());
        Assert.assertEquals(1, threeCounter.get());
    }

    @EventReceiver
    private void receiveSimple(final String value) {
        simpleCounter.incrementAndGet();
    }

    @MethodWithValue(identifier = 1)
    @EventReceiver
    private void receiveOne(final String value) {
        oneCounter.incrementAndGet();
    }

    @MethodWithValue(identifier = 2)
    @EventReceiver
    private void receiveTwo(final String value) {
        twoCounter.incrementAndGet();
    }
}
