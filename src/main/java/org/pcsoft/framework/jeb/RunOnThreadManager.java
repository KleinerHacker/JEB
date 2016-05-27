package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.annotation.handler.RunOnThread;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.exception.JEBAnnotationException;
import org.pcsoft.framework.jeb.exception.JEBThreadingException;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadNewThread;
import org.pcsoft.framework.jeb.utils.AnnotationReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public final class RunOnThreadManager extends JEBManagerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunOnThreadManager.class);

    private final Map<Annotation, RunOnThread> threadRunnerMap = new HashMap<>();

    RunOnThreadManager(final JEBConfiguration configuration) {
        super(configuration);

        LOGGER.info("Create new Thread Runner Manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Configuration: " + configuration);
        }

        //Register builtin
        registerRunOnThreadClass(RunOnThreadNewThread.class);

        for (final Class<? extends RunOnThread> threadRunnerClass : configuration.getThreadRunnerConfiguration().getThreadRunnerClassList()) {
            registerRunOnThreadClass(threadRunnerClass);
        }
    }

    public RunOnThread getRunOnThreadInstanceFor(final Annotation annotation) {
        return threadRunnerMap.get(annotation);
    }

    public void registerRunOnThreadClass(final Class<? extends RunOnThread> threadRunnerClass) {
        final Annotation threadRunnerAnnotation = AnnotationReaderUtils.findThreadRunnerAnnotation(threadRunnerClass, true);
        if (threadRunnerAnnotation == null)
            throw new JEBAnnotationException("Unable to find Thread Runner Qualified annotation on class: " + threadRunnerClass.getName());

        try {
            final RunOnThread runOnThread = threadRunnerClass.newInstance();
            runOnThread.setConfiguration(configuration);
            LOGGER.debug("Register new Thread Runner: " + threadRunnerClass.getName());
            threadRunnerMap.put(threadRunnerAnnotation, runOnThread);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JEBThreadingException("Unable to instantiate Thread Runner Class: " + threadRunnerClass.getName(), e);
        }
    }

    public void unregisterRunOnThreadClass(final Class<? extends RunOnThread> threadRunnerClass) {
        final Annotation threadRunnerAnnotation = AnnotationReaderUtils.findThreadRunnerAnnotation(threadRunnerClass, true);
        if (threadRunnerAnnotation == null)
            throw new JEBAnnotationException("Unable to find Thread Runner Qualified annotation on class: " + threadRunnerClass.getName());

        LOGGER.debug("Unregister Thread Runner: " + threadRunnerClass.getName());
        threadRunnerMap.remove(threadRunnerAnnotation);
    }
}
