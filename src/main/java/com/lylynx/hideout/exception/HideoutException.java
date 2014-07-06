package com.lylynx.hideout.exception;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 05.07.14
 * Time: 15:36
 */
public class HideoutException extends RuntimeException {
    public HideoutException(final String message) {
        super(message);
    }

    public HideoutException(final Throwable cause) {
        super(cause);
    }
}
