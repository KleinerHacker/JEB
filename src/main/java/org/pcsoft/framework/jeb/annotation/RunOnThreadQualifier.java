package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark an other annotation as qualifier for a {@link org.pcsoft.framework.jeb.annotation.handler.RunOnThreadHandler}.<br/>
 * Annotate your qualifier annotation with this annotation to mark it as Run-On-Thread Handler annotation. Than annotate the handler and the receiver
 * method, custom event bus class or sender method mon custom event bus class to use the linked handler.<br/>
 * <br/>
 * <b><code>Qualifier-Annotation</code></b>
 * <pre>
 *     &#64;Documented
 *     &#64;Retention(RUNTIME)
 *     &#64;Target({ElementType.TYPE, ElementType.METHOD})
 *     &#64;RunOnThreadQualifier
 *     public &#64;interface MyRunOnThreadAnnotation {
 *         ...
 *     }
 * </pre>
 * <b><code>Run-On-Thread Handler</code></b>
 * <pre>
 *     &#64;MyRunOnThreadAnnotation
 *     public class MyRunOnThreadHandler implements RunOnThreadHandler {
 *         ...
 *     }
 * </pre>
 * <b><code>Receiver-Method</code></b>
 * <pre>
 *     public class AnyClass {
 *         ...
 *         &#64;MyRunOnThreadAnnotation
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
public @interface RunOnThreadQualifier {
}
