package com.crafter.authoring.client.connector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <p>HttpConnector</p>
 */
public class HttpConnector implements Connector {

    private String authoringUrl;

    @Override
    public String performGet(String serviceUri, Map<String, String> parameters) throws IOException {
        String targetUrl = createTargetUrl(serviceUri, parameters);
        HttpGet httpGet = new HttpGet(targetUrl);
        return executeMethod(httpGet);
    }

    @Override
    public String performPost(String serviceUri, Map<String, String> parameters, String content) throws IOException {
        String targetUrl = createTargetUrl(serviceUri, parameters);
        HttpPost httpPost = new HttpPost(targetUrl);
        StringEntity reqEntity = new StringEntity(content);
        httpPost.setEntity(reqEntity);
        return executeMethod(httpPost);
    }


    @Override
    public String performMultipartPost(String serviceUri, Map<String, String> parameters, File file) throws IOException {
        String targetUrl = createTargetUrl(serviceUri, null);
        HttpPost httpPost = new HttpPost(targetUrl);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(file);
        builder.addPart("file", fileBody);
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                builder.addTextBody(key, parameters.get(key));
            }
        }
        HttpEntity reqEntity = builder.build();
        httpPost.setEntity(reqEntity);
        return executeMethod(httpPost);
    }

    /**
     * <p>execute a request</p>
     *
     * @param baseMethod a {@link org.apache.http.client.methods.HttpRequestBase} object
     * @return a {@link java.lang.String} object
     * @throws IOException
     */
    private String executeMethod(HttpRequestBase baseMethod) throws IOException {
        CloseableHttpResponse response = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            response = (CloseableHttpResponse) httpClient.execute(baseMethod);
            HttpEntity resEntity = response.getEntity();
            response.getStatusLine().getStatusCode();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(resEntity);
            } else {
                throw new IOException("status:" + response.getStatusLine().getStatusCode() +
                        "\n" + EntityUtils.toString(resEntity));
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * <p>create a target url</p>
     *
     * @param serviceUri a {@link java.lang.String} object
     * @param parameters a {@link java.util.Map} object
     * @return a {@link java.lang.String} object
     */
    private String createTargetUrl(String serviceUri, Map<String, String> parameters) {
        String queryString = createQueryString(parameters);
        return (queryString.length() > 0) ? this.authoringUrl + serviceUri + "?" + queryString : this.authoringUrl + serviceUri;
    }

    /**
     * <p>create a query string with the parameters</p>
     *
     * @param parameters a {@link java.util.Map} object
     * @return a {@link java.lang.String} object
     */
    private String createQueryString(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                if (sb.length() != 0) {
                    sb.append("&");
                }
                sb.append(key + "=" + parameters.get(key));
            }
        }
        return sb.toString();
    }

    /**
     * <p>Setter for the field <code>authoringUrl</code>.</p>
     * @param authoringUrl a {@link java.lang.String} object
     */
    public void setAuthoringUrl(String authoringUrl) {
        this.authoringUrl = authoringUrl;
    }


}
