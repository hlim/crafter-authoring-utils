package com.crafter.authoring.client.repository;

import com.crafter.authoring.client.exception.AuthenticationException;

import java.io.File;
import java.io.IOException;

/**
 * <p>ContentWriter</p>
 */
public interface ContentWriter {

    /**
     * <p>write a content to authoring</p>
     *
     * @param ticket a {@link java.lang.String} object
     * @param site a {@link java.lang.String} object
     * @param targetPath a {@link java.lang.String} object
     * @param file a {@link java.io.File} object
     * @throws AuthenticationException
     * @throws IOException
     */
    public void writeContent(String ticket, String site, String targetPath, File file) throws AuthenticationException, IOException;

    /**
     * <p>write an asset to authoring</p>
     *
     * @param ticket a {@link java.lang.String} object
     * @param site a {@link java.lang.String} object
     * @param targetPath a {@link java.lang.String} object
     * @param file a {@link java.io.File} object
     * @throws AuthenticationException
     * @throws IOException
     */
    public void writeContentAsset(String ticket, String site, String targetPath, File file) throws AuthenticationException, IOException;


}
