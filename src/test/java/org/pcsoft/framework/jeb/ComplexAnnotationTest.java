package org.pcsoft.framework.jeb;

import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.CustomEventBus;
import org.pcsoft.framework.jeb.qualifier.MethodOne;
import org.pcsoft.framework.jeb.qualifier.MethodTwo;

/**
 * Created by pfeifchr on 26.05.2016.
 */
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

    private MyEventBus eventBus = EventBusManager.create().getEventBus("my", MyEventBus.class);

    @Test
    public void test() {
        eventBus.registerReceiverClass(this);

        eventBus.send("hvfide");
        eventBus.sendToMethodOne("sdsdsd");
        eventBus.sendToMethodTwo("ndölsese");
        eventBus.sendToAll("nvsdö");
    }
}
