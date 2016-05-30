package org.pcsoft.framework.jeb.utils;

import org.pcsoft.framework.jeb.annotation.EventReceiverQualifier;
import org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier;
import org.pcsoft.framework.jeb.annotation.SurroundQualifier;
import org.pcsoft.framework.jeb.exception.JEBAnnotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils to read annotations
 */
public final class AnnotationReaderUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationReaderUtils.class);

    /**
     * Search for {@link EventReceiverQualifier}-Annotations on given element.
     * @param annotatedElement
     * @return
     */
    public static List<Annotation> findQualifierAnnotations(AnnotatedElement annotatedElement) {
        final Annotation[] annotations = annotatedElement.getAnnotations();
        final List<Annotation> qualifierAnnotationList = new ArrayList<>();
        for (final Annotation annotation : annotations) {
            if (annotation.annotationType().getAnnotation(EventReceiverQualifier.class) == null)
                continue;
            qualifierAnnotationList.add(annotation);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("> Find event receiver qualifier annotations: " + qualifierAnnotationList);
        }
        return qualifierAnnotationList;
    }

    /**
     * Search for {@link RunOnThreadQualifier}-Annotations on given element.
     * If element is an method, search will be continued on it declaring class and all super classes.
     * @param annotatedElement
     * @return
     */
    public static Annotation findRunOnThreadAnnotation(AnnotatedElement annotatedElement, boolean searchOnClass) {
        Annotation resultAnnotation = null;

        final Annotation[] annotations = annotatedElement.getAnnotations();
        for (final Annotation annotation : annotations) {
            if (annotation.annotationType().getAnnotation(RunOnThreadQualifier.class) != null) {
                if (resultAnnotation != null)
                    throw new JEBAnnotationException("Find more than one Run On Thread qualifier annotations on element: " + annotatedElement);
                resultAnnotation = annotation;
            }
        }

        //Find annotation on class or super classes
        if (searchOnClass) {
            if (resultAnnotation == null && annotatedElement instanceof Method) {
                Class currentClass = ((Method) annotatedElement).getDeclaringClass();
                while (currentClass != null && currentClass != Object.class && resultAnnotation == null) {
                    resultAnnotation = findRunOnThreadAnnotation(currentClass, true);
                    currentClass = currentClass.getSuperclass();
                }
            }
        }

        return resultAnnotation;
    }

    /**
     * Search for {@link SurroundQualifier}-Annotations on given element.
     * @param annotatedElement
     * @return
     */
    public static List<Annotation> findSurroundActionAnnotations(AnnotatedElement annotatedElement) {
        final Annotation[] annotations = annotatedElement.getAnnotations();
        final List<Annotation> qualifierAnnotationList = new ArrayList<>();
        for (final Annotation annotation : annotations) {
            if (annotation.annotationType().getAnnotation(SurroundQualifier.class) == null)
                continue;
            qualifierAnnotationList.add(annotation);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("> Find surround qualifier annotations: " + qualifierAnnotationList);
        }
        return qualifierAnnotationList;
    }

    private AnnotationReaderUtils() {
    }
}
