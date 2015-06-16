package com.crafter.authoring.client.exception;

/**
 * <p>AuthenticationException</p>
 */
public class AuthenticationException extends Exception {

    /**
     * <p>AuthenticationException constructor</p>
     */
    public AuthenticationException() {
        super();
    }

    /**
     * <p>AuthenticationException constructor</p>
     * @param exception a {@link java.lang.Exception} object
     */
    public AuthenticationException(Exception exception) {
        super(exception);
    }

    /**
     * <p>AuthenticationException constructor</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * <p>AuthenticationException constructor</p>
     *
     * @param message a {@link java.lang.String} object
     * @param exception a {@link java.lang.Exception} object
     */
    public AuthenticationException(String message, Exception exception) {
        super(message, exception);
    }
}
