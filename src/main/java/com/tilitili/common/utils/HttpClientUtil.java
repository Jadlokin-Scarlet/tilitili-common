package com.tilitili.common.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isBlank;

/**
 * 基于httpClient4.3.1的http工具
 */
public class HttpClientUtil {

    private static final Log log = LogFactory.getLog(HttpClientUtil.class);

    private static final int TIME_OUT = 10000;
    private static CloseableHttpClient httpclient = null;

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();

        SSLContextBuilder builder = new SSLContextBuilder();
        SSLConnectionSocketFactory sslsf = null;
        try {
            builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, new AllowAllHostnameVerifier());
        } catch (Exception ignored) { }

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config).setRetryHandler(new StandardHttpRequestRetryHandler()).build();
    }

    /**
     * httpGet
     */
    public static String httpGet(String url, String charSet, String cookie) {
        HttpGet httpGet = new HttpGet(url);

        charSet = isBlank(charSet) ? "UTF-8":charSet;
        if (!isBlank(cookie)) {
            httpGet.addHeader("Cookie", cookie);
        }
        try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, charSet);
            }
        } catch (Exception e) {
            log.error("[httpGet] got an Exception, url " + url, e);
        }
        return null;
    }

    /**
     * httpPost
     * @param params 传入p1=v1&p2=v2的kv内容
     */
    public static String httpPost(String url, Map<String, String> params, Map<String, String> headers, String charSet,
                                  String cookie) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        charSet = isBlank(charSet) ? "UTF-8":charSet;

        // 设置Header信息
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.addHeader(header.getKey(), header.getValue());
            }
        }

        if (!isBlank(cookie)) {
            httpPost.addHeader("Cookie", cookie);
        }

        // 设置请求体
        if (params != null && params.size() > 0) {
            List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(nvps,"utf-8");
            } catch (UnsupportedEncodingException ignored) { }
            httpPost.setEntity(entity);
        }

        try {
            httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, charSet);
            }
        } catch (Exception e) {
            log.error("[httpPost] got an Exception", e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception ignored) { }
            }
        }
        return null;
    }

    /**
     * 发送json格式体内容的httpPost
     */
    public static String httpPost(String url, String json, Map<String, String> headers, String charSet,
                                  String cookie) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        charSet = isBlank(charSet) ? "UTF-8":charSet ;

        // 设置Header信息
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.addHeader(header.getKey(), header.getValue());
            }
        }

        if (!isBlank(cookie)) {
            httpPost.addHeader("Cookie", cookie);
        }

        // 设置请求体
        if (!isBlank(json)) {
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            entity.setContentEncoding(charSet);
            httpPost.setEntity(entity);
        }

        try {
            httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, charSet);
            }
        } catch (Exception e) {
            log.error("[httpPost] got an Exception", e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception ignored) { }
            }
        }
        return null;
    }

}

