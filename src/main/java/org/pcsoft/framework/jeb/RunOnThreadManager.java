package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.exception.JEBAnnotationException;
import org.pcsoft.framework.jeb.exception.JEBThreadingException;
import org.pcsoft.framework.jeb.annotation.handler.RunOnThreadNewThreadHandler;
import org.pcsoft.framework.jeb.utils.AnnotationReaderUtils;
import org.pcsoft.framework.jeb.utils.ClassScannerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for Run-On-Thread system
 */
public final class RunOnThreadManager extends JEBManagerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunOnThreadManager.class);

    private final Map<Annotation, RunOnThreadHandler> runOnThreadHandlerMap = new HashMap<>();

    RunOnThreadManager(final JEBConfiguration configuration) {
        super(configuration);
        final JEBConfiguration.RunOnThreadConfiguration runOnThreadConfiguration = configuration.getRunOnThreadConfiguration();

        LOGGER.info("Create new Run On Thread Manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Configuration: " + configuration);
        }

        //Register builtin
        registerRunOnThreadHandlerClass(RunOnThreadNewThreadHandler.class);

        for (final Class<? extends RunOnThreadHandler> runOnThreadHandlerClass : runOnThreadConfiguration.getRunOnThreadHandlerClassList()) {
            registerRunOnThreadHandlerClass(runOnThreadHandlerClass);
        }

        if (runOnThreadConfiguration.getClasspathScanningConfiguration() != null) {
            final List<Class<? extends RunOnThreadHandler>> classList = ClassScannerUtils
                    .findRunOnThreadHandlerClasses(runOnThreadConfiguration.getClasspathScanningConfiguration().getPackageRestriction());
            for (final Class<? extends RunOnThreadHandler> runOnThreadHandlerClass : classList) {
                registerRunOnThreadHandlerClass(runOnThreadHandlerClass);
            }
        }
    }

    /**
     * Returns the fit instance for a Run-On-Thread annotation, see {@link org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier}
     * @param annotation
     * @return
     */
    public RunOnThreadHandler getRunOnThreadHandlerInstanceFor(final Annotation annotation) {
        return runOnThreadHandlerMap.get(annotation);
    }

    /**
     * Register a new {@link RunOnThreadHandler}
     * @param runOnThreadHandlerClass
     */
    public void registerRunOnThreadHandlerClass(final Class<? extends RunOnThreadHandler> runOnThreadHandlerClass) {
        final Annotation threadRunnerAnnotation = AnnotationReaderUtils.findRunOnThreadAnnotation(runOnThreadHandlerClass, true);
        if (threadRunnerAnnotation == null)
            throw new JEBAnnotationException("Unable to find Run On Thread Handler annotation on class: " + runOnThreadHandlerClass.getName());

        try {
            final RunOnThreadHandler runOnThreadHandler = runOnThreadHandlerClass.newInstance();
            runOnThreadHandler.setConfiguration(configuration);
            LOGGER.debug("Register new Thread Runner: " + runOnThreadHandlerClass.getName());
            runOnThreadHandlerMap.put(threadRunnerAnnotation, runOnThreadHandler);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JEBThreadingException("Unable to instantiate Run On Thread Handler Class: " + runOnThreadHandlerClass.getName(), e);
        }
    }

    /**
     * Unregister a {@link RunOnThreadHandler}
     * @param runOnThreadHandlerClass
     */
    public void unregisterRunOnThreadHandlerClass(final Class<? extends RunOnThreadHandler> runOnThreadHandlerClass) {
        final Annotation threadRunnerAnnotation = AnnotationReaderUtils.findRunOnThreadAnnotation(runOnThreadHandlerClass, true);
        if (threadRunnerAnnotation == null)
            throw new JEBAnnotationException("Unable to find Run On Thread Handler annotation on class: " + runOnThreadHandlerClass.getName());

        LOGGER.debug("Unregister Run On Thread Handler: " + runOnThreadHandlerClass.getName());
        runOnThreadHandlerMap.remove(threadRunnerAnnotation);
    }
}
