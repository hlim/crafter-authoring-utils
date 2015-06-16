package com.crafter.authoring.client.connector;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * <p>Connector Interface</p>
 */
public interface Connector {

    /**
     * <p>perform get request</p>
     *
     * @param targetUrl a {@link java.lang.String} object
     * @param parameters a {@link java.util.Map} object
     * @return  a {@link java.lang.String} object
     */
    public String performGet(String targetUrl, Map<String, String> parameters) throws IOException;

    /**
     * <p>perform multipart post request</p>
     *
     * @param targetUrl a {@link java.lang.String} object
     * @param parameters a {@link java.util.Map} object
     * @param file a {@link java.io.File} object
     * @return  a {@link java.lang.String} object
     * @throws IOException
     */
    public String performMultipartPost(String targetUrl, Map<String, String> parameters, File file) throws IOException;

    /**
     * <p>perform post request</p>
     *
     * @param targetUrl a {@link java.lang.String} object
     * @param parameters a {@link java.util.Map} object
     * @param content a {@link java.lang.String} object
     * @return  a {@link java.lang.String} object
     * @throws IOException
     */
    public String performPost(String targetUrl, Map<String, String> parameters, String content) throws IOException;
}
