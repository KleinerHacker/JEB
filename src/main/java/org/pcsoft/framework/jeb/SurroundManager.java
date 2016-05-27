package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.annotation.handler.SurroundAction;
import org.pcsoft.framework.jeb.annotation.handler.SurroundActionMethodLogger;
import org.pcsoft.framework.jeb.annotation.handler.SurroundActionTimeLogger;
import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.exception.JEBAnnotationException;
import org.pcsoft.framework.jeb.exception.JEBSurroundException;
import org.pcsoft.framework.jeb.utils.AnnotationReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public final class SurroundManager extends JEBManagerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurroundManager.class);

    private final Map<Class<? extends Annotation>, SurroundAction> surroundActionMap = new HashMap<>();

    SurroundManager(JEBConfiguration configuration) {
        super(configuration);

        LOGGER.info("Create new Surround Manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Configuration: " + configuration);
        }

        //Register builtin
        registerSurroundActionClass(SurroundActionMethodLogger.class);
        registerSurroundActionClass(SurroundActionTimeLogger.class);

        for (final Class<? extends SurroundAction> surroundActionClass : configuration.getSurroundConfiguration().getSurroundActionClassList()) {
            registerSurroundActionClass(surroundActionClass);
        }
    }

    public SurroundAction getSurroundActionInstanceFor(final Class<? extends Annotation> annotationClass) {
        return surroundActionMap.get(annotationClass);
    }

    public void registerSurroundActionClass(final Class<? extends SurroundAction> surroundActionClass) {
        final List<Annotation> surroundActionAnnotations = AnnotationReaderUtils.findSurroundActionAnnotations(surroundActionClass);
        if (surroundActionAnnotations.isEmpty())
            throw new JEBAnnotationException("Unable to find any surround action annotation on class: " + surroundActionClass.getName());
        if (surroundActionAnnotations.size() > 1)
            throw new JEBAnnotationException("Found more than one surround action annotation on class: " + surroundActionClass.getName());

        try {
            final SurroundAction surroundAction = surroundActionClass.newInstance();
            surroundAction.setConfiguration(configuration);
            LOGGER.debug("Register new Surround Action: " + surroundActionClass.getName());
            final Annotation annotation = surroundActionAnnotations.get(0);
            surroundActionMap.put(annotation.getClass(), surroundAction);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JEBSurroundException("Unable to instantiate surround action class: " + surroundActionClass.getName(), e);
        }
    }

    public void unregisterSurroundActionClass(final Class<? extends SurroundAction> surroundActionClass) {
        final List<Annotation> surroundActionAnnotations = AnnotationReaderUtils.findSurroundActionAnnotations(surroundActionClass);
        if (surroundActionAnnotations.isEmpty())
            throw new JEBAnnotationException("Unable to find any surround action annotation on class: " + surroundActionClass.getName());
        if (surroundActionAnnotations.size() > 1)
            throw new JEBAnnotationException("Found more than one surround action annotation on class: " + surroundActionClass.getName());

        surroundActionMap.remove(surroundActionAnnotations.get(0));
    }
}
