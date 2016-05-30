package org.pcsoft.framework.jeb.utils;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassMatchProcessor;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for the class scanner automatic
 */
public final class ClassScannerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassScannerUtils.class);

    public static List<Class<? extends RunOnThreadHandler>> findRunOnThreadHandlerClasses(final String packageRestriction) {
        LOGGER.debug("Start Class Scanning for Run On Thread Handler with restriction: " + packageRestriction);

        final List<Class<? extends RunOnThreadHandler>> resultList = new ArrayList<>();
        (packageRestriction == null ? new FastClasspathScanner() : new FastClasspathScanner(packageRestriction))
                .matchAllClasses(new ClassMatchProcessor() {
                    @Override
                    public void processMatch(Class<?> aClass) {
                        if (ReflectionUtils.isClassImplement(RunOnThreadHandler.class, aClass) && !Modifier.isAbstract(aClass.getModifiers()) && !aClass.isInterface()) {
                            resultList.add((Class<? extends RunOnThreadHandler>) aClass);
                            LOGGER.trace("> " + aClass.getName());
                        }
                    }
                })
                .scan();

        return resultList;
    }

    public static List<Class<? extends SurroundHandler>> findSurroundHandlerClasses(final String packageRestriction) {
        LOGGER.debug("Start Class Scanning for Surround Handler with restriction: " + packageRestriction);

        final List<Class<? extends SurroundHandler>> resultList = new ArrayList<>();
        (packageRestriction == null ? new FastClasspathScanner() : new FastClasspathScanner(packageRestriction))
                .matchAllClasses(new ClassMatchProcessor() {
                    @Override
                    public void processMatch(Class<?> aClass) {
                        if (ReflectionUtils.isClassImplement(SurroundHandler.class, aClass) && !Modifier.isAbstract(aClass.getModifiers()) && !aClass.isInterface()) {
                            resultList.add((Class<? extends SurroundHandler>) aClass);
                            LOGGER.trace("> " + aClass.getName());
                        }
                    }
                })
                .scan();

        return resultList;
    }

    private ClassScannerUtils() {
    }
}
