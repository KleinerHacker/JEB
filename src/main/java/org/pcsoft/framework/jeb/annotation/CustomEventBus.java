package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark an interface, extends from {@link org.pcsoft.framework.jeb.EventBus}, as custom Event Bus class, see {@link Sender}<br/>
 * <br/>
 * <pre>
 *     &#64;CustomEventBus
 *     public interface MyEventBus extends EventBus {
 *         ...
 *     }
 * </pre>
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CustomEventBus {

    /**
     * Annotation to mark an interface method in a custom Event Bus ({@link CustomEventBus}) as sender method.<br/>
     * This method must be accept one parameter of any type and can annotated with one ore more qualifier annotations, see
     * {@link EventReceiverQualifier}, {@link RunOnThreadQualifier}, {@link SurroundQualifier}
     * <pre>
     *     &#64;CustomEventBus
     *     public interface MyEventBus extends EventBus {
     *         ...
     *
     *         &#64;CustomEventBus.Sender
     *         void sendToAny(final Object value);
     *
     *         ...
     *     }
     * </pre>
     */
    @Documented
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface Sender {

    }
}
