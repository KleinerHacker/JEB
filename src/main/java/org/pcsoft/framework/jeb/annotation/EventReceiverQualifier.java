package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to create a qualifier annotation.<br/>
 * Annotate a custom annotation with this qualifier annotation to mark it as a special Event Receiver qualifier annotation for event receiver methods.
 * Annotate the receiver method and a method on a custom Event Bus to link these elements.<br/>
 * <br/>
 * <b><code>Qualifier-Annotation</code></b>
 * <pre>
 *     &#64;Documented
 *     &#64;Retention(RUNTIME)
 *     &#64;Target(METHOD)
 *     &#64;EventReceiverQualifier
 *     public &#64;interface MyQualifierAnnotation {
 *         ...
 *     }
 * </pre>
 * <b><code>Custom Event Bus</code></b>
 * <pre>
 *     &#64;CustomEventBus
 *     public interface MyEventBus extends EventBus {
 *         &#64;MyQualifierAnnotation
 *         &#64;CustomEventBus.Sender
 *         void sendToMy(final Object value);
 *     }
 * </pre>
 * <b><code>Receiver-Method</code></b>
 * <pre>
 *     public class AnyClass {
 *         ...
 *         &#64;MyQualifierAnnotation
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
@Target(ANNOTATION_TYPE)
public @interface EventReceiverQualifier {
}
