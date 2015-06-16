package com.crafter.authoring.client.repository;

import com.crafter.authoring.client.connector.Connector;
import com.crafter.authoring.client.exception.AuthenticationException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>AlfrescoContentWriter</p>
 */
public class AlfrescoContentWriter implements ContentWriter{

    private Connector connector;
    private String writeContentUri;
    private String writeAssetUri;

    @Override
    public void writeContent(String ticket, String site, String targetPath, File file) throws AuthenticationException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("site", site);
        params.put("path", targetPath);
        params.put("fileName", file.getName());
        params.put("createFolders", "true");
        params.put("edit", "true");
        params.put("unlock", "true");
        params.put("alf_ticket", ticket);
        String content = FileUtils.readFileToString(file, "UTF-8");
        Pattern pattern = Pattern.compile("\\<content\\-type\\>([a-zA-Z0-9\\-\\/]+)\\</content\\-type\\>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            params.put("contentType", matcher.group(1));
            break;
        }
        connector.performPost(this.writeContentUri, params, content);

    }

    @Override
    public void writeContentAsset(String ticket, String site, String targetPath, File file) throws AuthenticationException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("site", site);
        params.put("path", targetPath);
        connector.performMultipartPost(this.writeAssetUri + "?alf_ticket=" + ticket, params, file);
    }

    /**
     * <p>Setter for the field <code>connector</code>.</p>
     * @param connector a {@link }
     */
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * <p>Setter for the field <code>writeAssetUri</code>.</p>
     * @param writeAssetUri a {@link java.lang.String} object
     */
    public void setWriteAssetUri(String writeAssetUri) {
        this.writeAssetUri = writeAssetUri;
    }

    /**
     * <p>Setter for the field <code>writeContentUri</code>.</p>
     * @param writeContentUri a {@link java.lang.String} object
     */
    public void setWriteContentUri(String writeContentUri) {
        this.writeContentUri = writeContentUri;
    }

}

