package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark a method o any class as a receiver method for the Event Bus.<br/>
 * This method must accept one parameter of any type (event bus select method by type) and optional one or more qualifier annotations, like
 * {@link EventReceiverQualifier}, {@link RunOnThreadQualifier}, {@link SurroundQualifier}. Method must not be public.<br/>
 * <br/>
 * <pre>
 *     public class AnyClass {
 *         ...
 *         &#64;EventReceiver
 *         private void onReceive(final String value) {
 *             ...
 *         }
 *         ...
 *     }
 * </pre>
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface EventReceiver {
}
