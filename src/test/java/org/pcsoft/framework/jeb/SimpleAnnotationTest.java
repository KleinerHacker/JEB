package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.type.listener.ReceiverHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleAnnotationTest {

    private EventBus eventBus = EventBusManager.create().getEventBus();
    private final AtomicInteger stringCounter = new AtomicInteger(0), integerCounter = new AtomicInteger(0), booleanCounter = new AtomicInteger(0),
            objectCounter = new AtomicInteger(0);

    @Test
    public void test() {
        eventBus.registerReceiverClass(this);

        eventBus.registerReceiverMethod(Integer.class, new ReceiverHandler<Integer>() {
            @Override
            public void onReceive(Integer value) {
                integerCounter.incrementAndGet();
            }
        });

        eventBus.send("Hallo");
        Assert.assertEquals(1, stringCounter.get());
        Assert.assertEquals(0, integerCounter.get());
        Assert.assertEquals(0, booleanCounter.get());
        Assert.assertEquals(1, objectCounter.get());

        eventBus.send(true);
        Assert.assertEquals(1, stringCounter.get());
        Assert.assertEquals(0, integerCounter.get());
        Assert.assertEquals(1, booleanCounter.get());
        Assert.assertEquals(2, objectCounter.get());

        eventBus.send(10);
        Assert.assertEquals(1, stringCounter.get());
        Assert.assertEquals(1, integerCounter.get());
        Assert.assertEquals(1, booleanCounter.get());
        Assert.assertEquals(3, objectCounter.get());

        eventBus.send('c');
        Assert.assertEquals(1, stringCounter.get());
        Assert.assertEquals(1, integerCounter.get());
        Assert.assertEquals(1, booleanCounter.get());
        Assert.assertEquals(4, objectCounter.get());
    }

    @EventReceiver
    private void onReceiveString(final String value) {
        stringCounter.incrementAndGet();
    }

    @EventReceiver
    private void onReceiveBoolean(final boolean value) {
        booleanCounter.incrementAndGet();
    }

    @EventReceiver
    private void onReceiveAny(final Object value) {
        objectCounter.incrementAndGet();
    }

}
