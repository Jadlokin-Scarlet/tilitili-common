package com.tilitili.common.utils;


import com.tilitili.common.entity.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.http.util.TextUtils.isBlank;

/**
 * 基于httpClient4.3.1的http工具
 */
@Slf4j
public class HttpClientUtil {

    private static final int TIME_OUT = 10000;
    private static final CloseableHttpClient httpClient;

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();
        httpClient = HttpClients.custom().setDefaultRequestConfig(config).setRetryHandler(new StandardHttpRequestRetryHandler()).build();
    }

    /**
     * httpGet
     */
    public static String httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, UTF_8);
            } else {
                log.warn("[httpGet] got an Exception resp={}", httpResponse.toString());
            }
        } catch (IOException e) {
            log.error("[httpGet] got an Exception, url " + url, e);
        }
        return null;
    }

    /**
     * 发送无内容的httpPost
     */
    public static String httpPost(String url) {
        return httpPost(url, null, null, null, false);
    }

    /**
     * httpPost
     * @param params 传入p1=v1&p2=v2的kv内容
     */
    public static String httpPost(String url, Map<String, String> params) {
        return httpPost(url, params, null, null, false);
    }

    /**
     * 发送json格式体内容的httpPost
     */
    public static String httpPost(String url, String json) {
        return httpPost(url, null, json, null, false);
    }

    public static String httpPostProxy(String url, Map<String, String> params) {
        return httpPost(url, params, null, null, true);
    }

    public static String httpPostWithFile(String url, Map<String, String> params, UploadFile file) {
        return httpPost(url, params, null, file, false);
    }

    private static String httpPost(String url, Map<String, String> params, String json, UploadFile uploadFile, Boolean useProxy) {
        HttpPost httpPost = new HttpPost(url);

        if (! CollectionUtils.isEmpty(params)) {
            List<BasicNameValuePair> form = new ArrayList<>();
            for (String key : params.keySet()) {
                form.add(new BasicNameValuePair(key, params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(form, UTF_8));
        }

        if (!isBlank(json)) {
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
        }

        if (uploadFile != null) {
            String fileName = uploadFile.getFileName();
            String fileType = uploadFile.getFileType();
            File file = uploadFile.getFile();
            BufferedImage image = uploadFile.getBufferedImage();

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            if (file != null) {
                entityBuilder.addPart(fileName, new FileBody(file, ContentType.MULTIPART_FORM_DATA));
            } else {
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(image, fileType, stream);
                    entityBuilder.addBinaryBody(fileName, stream.toByteArray(), ContentType.create("image/"+fileType), fileName + "." + fileType);
                } catch (IOException e) {
                    log.error("图片异常", e);
                }
            }
            for (String key : params.keySet()) {
                entityBuilder.addPart(key, new StringBody(params.get(key), ContentType.MULTIPART_FORM_DATA));
            }
            httpPost.setEntity(entityBuilder.build());
        }

        CloseableHttpClient httpClient;
        if (useProxy) {
            HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");
            RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        } else {
            httpClient = HttpClientUtil.httpClient;
        }

        try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, UTF_8);
            } else {
                log.warn("[httpPost] got an Exception resp={}", httpResponse.toString());
            }
        } catch (IOException e) {
            log.error("[httpPost] got an Exception", e);
        }
        return null;
    }
}

