package org.pcsoft.framework.jeb.exception;

/**
 * Created by pfeifchr on 27.05.2016.
 */
public class JEBThreadingException extends JEBException {

    public JEBThreadingException() {
    }

    public JEBThreadingException(Throwable cause) {
        super(cause);
    }

    public JEBThreadingException(String message) {
        super(message);
    }

    public JEBThreadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
