package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.annotation.handler.SurroundHandler;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandlerMethodLogger;
import org.pcsoft.framework.jeb.annotation.handler.SurroundHandlerTimeLogger;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.exception.JEBAnnotationException;
import org.pcsoft.framework.jeb.exception.JEBSurroundException;
import org.pcsoft.framework.jeb.utils.AnnotationReaderUtils;
import org.pcsoft.framework.jeb.utils.ClassScannerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for surround system
 */
public final class SurroundManager extends JEBManagerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurroundManager.class);

    private final Map<Class<? extends Annotation>, SurroundHandler> surroundHandlerMap = new HashMap<>();

    SurroundManager(JEBConfiguration configuration) {
        super(configuration);
        final JEBConfiguration.SurroundConfiguration surroundConfiguration = configuration.getSurroundConfiguration();

        LOGGER.info("Create new Surround Manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Configuration: " + configuration);
        }

        //Register builtin
        registerSurroundHandlerClass(SurroundHandlerMethodLogger.class);
        registerSurroundHandlerClass(SurroundHandlerTimeLogger.class);

        for (final Class<? extends SurroundHandler> surroundActionClass : surroundConfiguration.getSurroundHandlerClassList()) {
            registerSurroundHandlerClass(surroundActionClass);
        }

        if (surroundConfiguration.getClasspathScanningConfiguration() != null) {
            final List<Class<? extends SurroundHandler>> classList = ClassScannerUtils
                    .findSurroundHandlerClasses(surroundConfiguration.getClasspathScanningConfiguration().getPackageRestriction());
            for (final Class<? extends SurroundHandler> surroundActionClass : classList) {
                registerSurroundHandlerClass(surroundActionClass);
            }
        }
    }

    /**
     * Returns a fit handler for the surround annotation, see {@link org.pcsoft.framework.jeb.annotation.SurroundQualifier}
     * @param annotationClass
     * @return
     */
    public SurroundHandler getSurroundHandlerInstanceFor(final Class<? extends Annotation> annotationClass) {
        return surroundHandlerMap.get(annotationClass);
    }

    /**
     * Register a new {@link SurroundHandler}
     * @param surroundHandlerClass
     */
    public void registerSurroundHandlerClass(final Class<? extends SurroundHandler> surroundHandlerClass) {
        final List<Annotation> surroundActionAnnotations = AnnotationReaderUtils.findSurroundActionAnnotations(surroundHandlerClass);
        if (surroundActionAnnotations.isEmpty())
            throw new JEBAnnotationException("Unable to find any surround handler annotation on class: " + surroundHandlerClass.getName());
        if (surroundActionAnnotations.size() > 1)
            throw new JEBAnnotationException("Found more than one surround handler annotation on class: " + surroundHandlerClass.getName());

        try {
            final SurroundHandler surroundHandler = surroundHandlerClass.newInstance();
            surroundHandler.setConfiguration(configuration);
            LOGGER.debug("Register new Surround Handler: " + surroundHandlerClass.getName());
            final Annotation annotation = surroundActionAnnotations.get(0);
            surroundHandlerMap.put(annotation.getClass(), surroundHandler);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JEBSurroundException("Unable to instantiate surround action class: " + surroundHandlerClass.getName(), e);
        }
    }

    /**
     * Unregister a {@link SurroundHandler}
     * @param surroundHandlerClass
     */
    public void unregisterSurroundHandlerClass(final Class<? extends SurroundHandler> surroundHandlerClass) {
        final List<Annotation> surroundActionAnnotations = AnnotationReaderUtils.findSurroundActionAnnotations(surroundHandlerClass);
        if (surroundActionAnnotations.isEmpty())
            throw new JEBAnnotationException("Unable to find any surround handler annotation on class: " + surroundHandlerClass.getName());
        if (surroundActionAnnotations.size() > 1)
            throw new JEBAnnotationException("Found more than one surround handler annotation on class: " + surroundHandlerClass.getName());

        LOGGER.debug("Unregister Surround Handler: " + surroundHandlerClass.getName());
        surroundHandlerMap.remove(surroundActionAnnotations.get(0));
    }
}
