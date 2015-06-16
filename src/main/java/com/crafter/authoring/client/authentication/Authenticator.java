package com.crafter.authoring.client.authentication;

import com.crafter.authoring.client.exception.AuthenticationException;

/**
 * <p>Authenticator Interface</p>
 */
public interface Authenticator {

    /**
     * <p>authenticate the credentials given and return an authentication key</p>
     *
     * @param username a {@link java.lang.String} object
     * @param password a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public String authenticate(String username, String password) throws AuthenticationException;
}
