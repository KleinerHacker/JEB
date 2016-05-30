package org.pcsoft.framework.jeb.type.listener;

/**
 * Listener Interface to register a receiver method manually
 */
public interface ReceiverHandler<T> {
    /**
     * This method is called via {@link org.pcsoft.framework.jeb.EventBus}. This method can be annotated in implementation with qualifier annotations like
     * {@link org.pcsoft.framework.jeb.annotation.EventReceiverQualifier}, {@link org.pcsoft.framework.jeb.annotation.SurroundQualifier}, {@link org.pcsoft.framework.jeb.annotation.RunOnThreadQualifier}.<br/>
     * <br/>
     * <pre>
     *     public class AnyClass {
     *         ...
     *         private EventBus bus = EventBusManager.create().getEventBus();
     *
     *         ...
     *         private void doAny() {
     *             ...
     *             bus.registerReceiverMethod(String.class, new ReceiverHandler&lt;String> {
     *                  &#64;RunOnNewThread
     *                  &#64;SurroundMethodLogger
     *                  &#64;MyQualifier
     *                  &#64;Overwrite
     *                  public void onReceive(final String value) {
     *                      ...
     *                  }
     *             });
     *             ...
     *         }
     *         ...
     *     }
     * </pre>
     * @param value
     */
    void onReceive(final T value);
}
