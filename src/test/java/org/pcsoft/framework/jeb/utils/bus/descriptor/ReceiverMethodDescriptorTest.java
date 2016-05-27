package org.pcsoft.framework.jeb.utils.bus.descriptor;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.EventReceiver;
import org.pcsoft.framework.jeb.qualifier.receiver.MethodOne;
import org.pcsoft.framework.jeb.qualifier.receiver.MethodTwo;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public class ReceiverMethodDescriptorTest {

    @Test
    public void testSimpleMethod() throws Exception {
        final ReceiverMethodDescriptor receiverMethodDescriptor = new ReceiverMethodDescriptor(getClass().getDeclaredMethod("runSimple", String.class), this);
        Assert.assertEquals(String.class, receiverMethodDescriptor.getQualifierKey().getParameterClass());
        Assert.assertEquals(0, receiverMethodDescriptor.getQualifierKey().getQualifierAnnotationList().size());
    }

    @EventReceiver
    private void runSimple(final String value) {
    }

    @Test
    public void testQualifiedMethod() throws Exception {
        final ReceiverMethodDescriptor receiverMethodDescriptor = new ReceiverMethodDescriptor(getClass().getDeclaredMethod("runQualified", String.class), this);
        Assert.assertEquals(String.class, receiverMethodDescriptor.getQualifierKey().getParameterClass());
        Assert.assertEquals(2, receiverMethodDescriptor.getQualifierKey().getQualifierAnnotationList().size());
        Assert.assertEquals(MethodOne.class, receiverMethodDescriptor.getQualifierKey().getQualifierAnnotationList().get(0).annotationType());
        Assert.assertEquals(MethodTwo.class, receiverMethodDescriptor.getQualifierKey().getQualifierAnnotationList().get(1).annotationType());
    }

    @MethodOne
    @MethodTwo
    @EventReceiver
    private void runQualified(final String value) {
    }

}