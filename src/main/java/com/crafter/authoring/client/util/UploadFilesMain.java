package com.crafter.authoring.client.util;

import com.crafter.authoring.client.authentication.Authenticator;
import com.crafter.authoring.client.connector.HttpConnector;
import com.crafter.authoring.client.exception.AuthenticationException;
import com.crafter.authoring.client.repository.ContentWriter;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * <p>UploadFilesMain</p>
 */
public class UploadFilesMain {

    // hard-coding folder paths to prevent any user mistakes here. could improve this to read from properties
    private static final String PATH_PAGES = "/site/website";
    private static final String PATH_COMPONENTS = "/site/components";
    private static final String PATH_ASSETS = "/static-assets";
    private static final String PATH_TEMPLATES = "/templates";
    private static final String PATH_SCRIPTS = "/scripts";
    private static final String PATH_CLASSES = "/classes";

    public static void main(String [] args) throws Exception {
        if (args.length > 4) {
            startProcess(args[0], args[1], args[2], args[3], args[4]);
        } else {
            System.out.println("UploadFilesMain [alfrescoURL:http://localhost:8080/alfresco] [rootPath:/user/me/local] [site:acme] [username] [password]");
        }
    }

    public static void startProcess(String authoringUrl, String rootPath, String site, String username, String password) throws Exception {
        System.out.println("[UploadFilesMain] Alfresco URL: " + authoringUrl);
        System.out.println("[UploadFilesMain] Root path: " + rootPath);
        System.out.println("[UploadFilesMain] Site: " + site);
        System.out.println("[UploadFilesMain] Username: " + username);
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("classpath:application-context.xml");
        Authenticator authenticator = (Authenticator) context.getBean("Authenticator");
        ContentWriter writer = (ContentWriter) context.getBean("ContentWriter");
        HttpConnector connector = (HttpConnector) context.getBean("Connector");
        connector.setAuthoringUrl(authoringUrl);
        String ticket = authenticator.authenticate(username, password);

        File rootDir = new File(rootPath);
        if (rootDir.exists() && rootDir.isDirectory()) {
            writeContents(writer, ticket, new File(rootPath + PATH_PAGES), site, PATH_PAGES, false);
            writeContents(writer, ticket, new File(rootPath + PATH_COMPONENTS), site, PATH_COMPONENTS, false);
            writeContents(writer, ticket, new File(rootPath + PATH_ASSETS), site, PATH_ASSETS, true);
            writeContents(writer, ticket, new File(rootPath + PATH_TEMPLATES), site, PATH_TEMPLATES, true);
            writeContents(writer, ticket, new File(rootPath + PATH_SCRIPTS), site, PATH_SCRIPTS, true);
            writeContents(writer, ticket, new File(rootPath + PATH_CLASSES), site, PATH_CLASSES, true);
        } else {
            System.out.println("[UploadFilesMain]" + rootPath + " does not exist or is not a directory.");
        }
    }

    /**
     * <p>write contents to the repository</p>
     *
     * @param writer a {@link com.crafter.authoring.client.repository.ContentWriter} object
     * @param ticket a {@link java.lang.String} object
     * @param currDir  a {@link java.io.File} object
     * @param site a {@link java.lang.String} object
     * @param sitePath a {@link java.lang.String} object
     * @param isAssetFolder a boolean
     * @throws AuthenticationException
     */
    private static void writeContents(ContentWriter writer, String ticket, File currDir, String site,
                                      String sitePath, boolean isAssetFolder) throws AuthenticationException {
        if (currDir.exists() && currDir.isDirectory()) {
            String[] children = currDir.list();
            if (children != null && children.length > 0) {
                for (String childName : children) {
                    File childFile = new File(currDir.getAbsolutePath() + "/" + childName);
                    String childSitePath = sitePath + "/" + childName;
                    if (childFile.isDirectory()) {
                        writeContents(writer, ticket, childFile, site, childSitePath, isAssetFolder);
                    } else if (childFile.getName().matches(".+\\..+")) {
                        try {
                            if (isAssetFolder) {
                                System.out.println("[UploadFilesMain] Writing Asset: " + childSitePath);
                                writer.writeContentAsset(ticket, site, sitePath, childFile);
                            } else {
                                System.out.println("[UploadFilesMain] Writing Content: " + childSitePath);
                                writer.writeContent(ticket, site, childSitePath, childFile);
                            }
                        } catch (IOException e) {
                            System.out.println("[UploadFilesMain] Error while writing " + currDir.getAbsolutePath()
                                    + " to " + childSitePath + "\n" + e.getMessage());

                        }
                    }
                }
            }
        } else {
            System.out.println("[UploadFilesMain] " + currDir.getAbsolutePath() + " does not exist or is not a directory.");
        }
    }
}
