package org.pcsoft.framework.jeb.utils.bus.descriptor;

import org.apache.commons.lang.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represent a qualifier key, based on parameter class and qualifier annotations. Equals and Hashcode are implemented for this key.
 */
public final class ReceiverQualifierKey {
    private final Class<?> parameterClass;
    private final List<Annotation> qualifierAnnotationList = new ArrayList<>();

    public ReceiverQualifierKey(Class<?> parameterClass, Collection<Annotation> qualifierAnnotations) {
        this.parameterClass = parameterClass;
        this.qualifierAnnotationList.addAll(qualifierAnnotations);
    }


    public ReceiverQualifierKey(Class<?> parameterClass, Annotation... qualifierAnnotations) {
        this.parameterClass = parameterClass;
        this.qualifierAnnotationList.addAll(Arrays.asList(qualifierAnnotations));
    }

    /**
     * Type of parameter
     * @return
     */
    public Class<?> getParameterClass() {
        return parameterClass;
    }

    /**
     * List of qualifier annotations. Can be empty.
     * @return
     */
    public List<Annotation> getQualifierAnnotationList() {
        return qualifierAnnotationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiverQualifierKey that = (ReceiverQualifierKey) o;

        return (ClassUtils.primitiveToWrapper(parameterClass).isAssignableFrom(ClassUtils.primitiveToWrapper(that.parameterClass)) ||
                ClassUtils.primitiveToWrapper(that.parameterClass).isAssignableFrom(ClassUtils.primitiveToWrapper(parameterClass))) &&
                Objects.equals(qualifierAnnotationList, that.qualifierAnnotationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterClass, qualifierAnnotationList);
    }

    @Override
    public String toString() {
        return "ReceiverQualifierKey{" +
                "parameterClass=" + parameterClass +
                ", qualifierAnnotationList=" + qualifierAnnotationList +
                '}';
    }
}
