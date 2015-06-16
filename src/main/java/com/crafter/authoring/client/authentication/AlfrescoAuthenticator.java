package com.crafter.authoring.client.authentication;

import com.crafter.authoring.client.connector.Connector;
import com.crafter.authoring.client.exception.AuthenticationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>AlfrescoAuthenticator</p>
 */
public class AlfrescoAuthenticator implements Authenticator {

    private Connector connector;
    private String authenticationUri;

    @Override
    public String authenticate(String username, String password) throws AuthenticationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("u", username);
        params.put("pw", password);
        try {
            String response = this.connector.performGet(this.authenticationUri, params);
            return response.replaceAll("\\<([^\\>]+)\\>", "").replaceAll("[^a-zA-Z0-9_]+", "");
        } catch(IOException e) {
            throw new AuthenticationException(e);
        }
    }

    /**
     * <p>Setter for the field <code>connector</code>.</p>
     * @param connector a {@link }
     */
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * <p>Setter for the field <code>authenticationUri</code>.</p>
     * @param authenticationUri a {@link java.lang.String} object
     */
    public void setAuthenticationUri(String authenticationUri) {
        this.authenticationUri = authenticationUri;
    }
}
