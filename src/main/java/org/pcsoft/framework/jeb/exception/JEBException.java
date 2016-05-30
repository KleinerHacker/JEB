package org.pcsoft.framework.jeb.exception;

public abstract class JEBException extends RuntimeException {

    public JEBException() {
    }

    public JEBException(Throwable cause) {
        super(cause);
    }

    public JEBException(String message) {
        super(message);
    }

    public JEBException(String message, Throwable cause) {
        super(message, cause);
    }
}
