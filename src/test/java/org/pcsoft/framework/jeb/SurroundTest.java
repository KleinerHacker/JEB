package org.pcsoft.framework.jeb;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.annotation.handler.SurroundActionBase;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.qualifier.surround.SurroundMethod1;
import org.pcsoft.framework.jeb.qualifier.surround.SurroundMethod2;
import org.pcsoft.framework.jeb.qualifier.surround.SurroundMethodWithValue;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public class SurroundTest {
    private static final String VAL_TEST = "Test";
    private static final String VAL_PRODUCTION = "Production";

    private static final AtomicInteger method1Counter = new AtomicInteger(0), method2Counter = new AtomicInteger(0), valueTestCounter = new AtomicInteger(0),
            valueProductionCounter = new AtomicInteger(0);

    @SurroundMethod1
    public static final class SurroundActionMethod1 extends SurroundActionBase<SurroundMethod1> {
        @Override
        public void onPostInvoke(SurroundMethod1 annotation, Method method, Object value) {
            method1Counter.incrementAndGet();
        }

        @Override
        public void onPreInvoke(SurroundMethod1 annotation, Method method, Object value) {
            method1Counter.incrementAndGet();
        }
    }

    @SurroundMethod2
    public static final class SurroundActionMethod2 extends SurroundActionBase<SurroundMethod2> {
        @Override
        public void onPostInvoke(SurroundMethod2 annotation, Method method, Object value) {
            method2Counter.incrementAndGet();
        }

        @Override
        public void onPreInvoke(SurroundMethod2 annotation, Method method, Object value) {
            method2Counter.incrementAndGet();
        }
    }

    @SurroundMethodWithValue
    public static final class SurroundActionMethodWithValue extends SurroundActionBase<SurroundMethodWithValue> {
        @Override
        public void onPostInvoke(SurroundMethodWithValue annotation, Method method, Object value) {
            count(annotation.identifier());
        }

        @Override
        public void onPreInvoke(SurroundMethodWithValue annotation, Method method, Object value) {
            count(annotation.identifier());
        }

        private void count(String identifier) {
            switch (identifier) {
                case VAL_TEST:
                    valueTestCounter.incrementAndGet();
                    break;
                case VAL_PRODUCTION:
                    valueProductionCounter.incrementAndGet();
                    break;
            }
        }
    }

    private final JEBConfiguration configuration = JEBConfigurationFactory.createDefaultBuilder()
            .withAdditionalSurroundAction(SurroundActionMethod1.class)
            .withAdditionalSurroundAction(SurroundActionMethod2.class)
            .withAdditionalSurroundAction(SurroundActionMethodWithValue.class)
            .build();
    private final EventBus eventBus = EventBusManager.create(configuration).getEventBus();

    @Test
    public void test() {
        eventBus.registerReceiverClass(this);

        eventBus.send("test");
        Assert.assertEquals(2, method1Counter.get());
        Assert.assertEquals(0, method2Counter.get());
        Assert.assertEquals(0, valueProductionCounter.get());
        Assert.assertEquals(0, valueTestCounter.get());

        eventBus.send(123);
        Assert.assertEquals(2, method1Counter.get());
        Assert.assertEquals(2, method2Counter.get());
        Assert.assertEquals(0, valueProductionCounter.get());
        Assert.assertEquals(0, valueTestCounter.get());

        eventBus.send(true);
        Assert.assertEquals(2, method1Counter.get());
        Assert.assertEquals(2, method2Counter.get());
        Assert.assertEquals(2, valueProductionCounter.get());
        Assert.assertEquals(0, valueTestCounter.get());

        eventBus.send('C');
        Assert.assertEquals(2, method1Counter.get());
        Assert.assertEquals(2, method2Counter.get());
        Assert.assertEquals(2, valueProductionCounter.get());
        Assert.assertEquals(2, valueTestCounter.get());
    }

    @SurroundMethod1
    @EventReceiver
    private void on1(final String value) {

    }

    @SurroundMethod2
    @EventReceiver
    private void on2(final Integer value) {

    }

    @SurroundMethodWithValue(identifier = VAL_PRODUCTION)
    @EventReceiver
    private void onProduction(final Boolean value) {

    }

    @SurroundMethodWithValue(identifier = VAL_TEST)
    @EventReceiver
    private void onTest(final Character value) {

    }
}
