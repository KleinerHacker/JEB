package org.pcsoft.framework.jeb.utils;

import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;

import java.util.List;

public class ClassScannerUtilsTest {
    @Test
    public void findRunOnThreadHandlerClassesTest() throws Exception {
        final List<Class<? extends RunOnThreadHandler>> classList = ClassScannerUtils.findRunOnThreadHandlerClasses("org.pcsoft.framework.jeb.qualifier.threading");
        System.out.println(classList);

        Assert.assertNotNull(classList);
        Assert.assertEquals(4, classList.size());
    }

    @Test
    public void findSurroundHandlerClassesTest() throws Exception {
        final List<Class<? extends SurroundHandler>> classList = ClassScannerUtils.findSurroundHandlerClasses("org.pcsoft.framework.jeb");
        System.out.println(classList);

        Assert.assertNotNull(classList);
        Assert.assertEquals(5, classList.size());
    }

}