package org.pcsoft.framework.jeb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark an other custom annotation as qualifier for a surround handler class.<br/>
 * Annotate your custom annotation with this annotation to mark it as surround. Annotate the handler class and the receiver method,
 * custom event bus class or sender method on custom event bus class.<br/>
 * <br/>
 * <b><code>Qualifier-Annotation</code></b>
 * <pre>
 *     &#64;Documented
 *     &#64;Retention(RUNTIME)
 *     &#64;Target({ElementType.METHOD, ElementType.TYPE})
 *     &#64;SurroundQualifier
 *     public &#64;interface MySurroundAnnotation {
 *      ...
 *     }
 * </pre>
 * <b><code>Handler-Class</code></b>
 * <pre>
 *     &#64;MySurroundAnnotation
 *     public class MySurroundHandler implements SurroundHandler {
 *         ...
 *     }
 * </pre>
 * <b><code>Receiver-Method</code></b>
 * <pre>
 *     public class AnyClass {
 *         ...
 *         &#64;MySurroundAnnotation
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
public @interface SurroundQualifier {
}
