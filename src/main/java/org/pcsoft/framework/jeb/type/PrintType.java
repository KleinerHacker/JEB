package org.pcsoft.framework.jeb.type;

/**
 * Print Types for logging with {@link org.pcsoft.framework.jeb.annotation.SurroundMethodLogger}, {@link org.pcsoft.framework.jeb.annotation.SurroundTimeLogger}
 */
public enum PrintType {
    /**
     * Print method name only
     */
    Method,
    /**
     * Print method and class name
     */
    MethodAndClass
}