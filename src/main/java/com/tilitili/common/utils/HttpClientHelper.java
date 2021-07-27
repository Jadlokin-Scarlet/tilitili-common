package com.tilitili.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 
 * @author yehang
 *
 */
@Slf4j
public class HttpClientHelper {
	public static int TIME_OUT_CONNECT =1000 *3;
	public static int TIME_OUT_READ =1000 *5;

	private final static int BUFFER = 1024; 
	

	protected static HttpClient httpClient = null;
	static {
		MultiThreadedHttpConnectionManager mgr = new MultiThreadedHttpConnectionManager();
		mgr.getParams().setDefaultMaxConnectionsPerHost(TIME_OUT_CONNECT);
		mgr.getParams().setMaxTotalConnections(200);
		httpClient = new HttpClient(mgr);
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 Safari/537.31");
	}
	
//	private static HttpClient httpClient = new HttpClient();
	private HttpClientHelper(){
	}
	
	public static HttpClient getHttpClient(){
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_CONNECT);
		return httpClient;
	}
	
	public static String getStringByUrl(String url, boolean needResponse){
		return processGet(getHttpClient(), null, url, needResponse, "", false);
	}
	
	public static String getStringByUrlEncode(String url, boolean needResponse){
		if(StringUtils.isEmpty(url)){
			return "";
		}
		int idx = url.indexOf("?");
		if(idx>0){
			String host  = url.substring(0,idx);
			String paramString = url.substring(idx+1);
			StringBuffer paramBuffer = new StringBuffer();
			paramBuffer.append(host);
			try{
				Map<String, String> map = new HashMap<String, String>();
				String[] params = paramString.split("&");
				for(String param : params){
					String[] kv = param.split("=");
					map.put(kv[0], StringUtils.isEmpty(kv[1])?"":java.net.URLEncoder.encode(kv[1], "UTF-8"));
				}

				for(Iterator<String> it = map.keySet().iterator(); it.hasNext();){
					String key = it.next();
					String value = map.get(key);
					if(paramBuffer.toString().indexOf("?") > 0)
						paramBuffer.append("&");
					else
						paramBuffer.append("?");
					paramBuffer.append(key).append("=").append(value);
				}
				url = paramBuffer.toString();
			}catch(Exception e){
				log.error("getStringByUrlEncode error:"+e.getMessage(),e);
			}
		}
		return processGet(getHttpClient(), null, url, needResponse, "", false);
	}
	
	public static String getStringByUrlAndCookie(String url, boolean needResponse, String cookie){
		return processGet(getHttpClient(), null, url, needResponse, cookie, true);
	}	
	
	public static void main(String args[]) throws Exception{
//		String url="http://ju.taobao.com/tg/home.htm?spm=608.2177197.1.1.pbwLSI&id=10000000037163";
		String url ="http://127.0.0.1:8080/hello";
		String html= getStringByUrlEncode(url, true);
		System.out.println(html);
		
	}
	
	public static String toRedirectURL(String location,URI lastUrl){
		try{
			if(location==null||location.trim().length()==0){
				location="/";
			}
			String tmp=location.toLowerCase();
			if(!tmp.startsWith("http://")&&!tmp.startsWith("https://")){
				if(lastUrl==null){
					throw new Exception("lastUrlisnull,cannotfindrelativeprotocolandhostname");
				}
				return new URI(lastUrl,location,false).toString();
			}
		}catch(Exception e){
			 log.error("toRedirectURL error:"+e.getMessage());
		}
		return location;		
	}
	
	
//	public static String processGet(HttpClient api, GetMethod get, String url, boolean needResponse, String cookie, boolean needAppendCookies) {
//		try {
//			if (api == null || url == null || url.equals("") )
//				return "";
//
//			byte[] responseBody = null;
//			String html = "";
//			if (get == null){
//				get = new GetMethod(url);
//				get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//				get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,TIME_OUT_IN_MILLISECONDS);
////				get.getParams().setSoTimeout(TIME_OUT_IN_MILLISECONDS);
//			}
//			
//			if (!cookie.equals("")&&cookie.length()>0)
//				get.setRequestHeader("Cookie", cookie);
//			
//			int statusCode = api.executeMethod(get);
//			log.error("processGet statusCode="+statusCode);
//            if (statusCode==HttpStatus.SC_MOVED_PERMANENTLY 
//            		|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY
//					|| statusCode == HttpStatus.SC_SEE_OTHER
//					|| statusCode == HttpStatus.SC_TEMPORARY_REDIRECT){
//                Header locationHeader = get.getResponseHeader("location");
//                log.error("processGet locationHeader="+(locationHeader==null?"locationHeader is null":"locationHeader is not null"));
//                String location = null;
//                if (locationHeader != null){
//                    location = locationHeader.getValue();
//                    log.error("processGet location="+location);
//                    location = toRedirectURL(location, new URI(url,false));
//                    log.error("processGet statusCode="+statusCode+" redirect url="+location);
//                    get = new GetMethod(location);
//                    get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//    				get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,TIME_OUT_IN_MILLISECONDS);
//                    api.executeMethod(get);
//                }
//            }
//
//			if (needAppendCookies) {
//				api.getState().addCookies(api.getState().getCookies());
//			}
//			if (needResponse) {
//				responseBody = get.getResponseBody();
//				html = new String(responseBody, "GBK");
//			}
//			return html;
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			log.error("processGet出错url="+url);
//		} finally {
//			get.releaseConnection();
//		}
//		return "";
//	}	
	
	
	public static String processGet(HttpClient client, GetMethod get, String url, boolean needResponse, String cookie, boolean needAppendCookies) {
		try {
			if (client == null || url == null || url.equals("") )
				return "";

			byte[] responseBody = null;
			String html = "";
			if (get == null){
				get = new GetMethod(url);
				get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
				get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,2000);
//				get.getParams().setSoTimeout(TIME_OUT_IN_MILLISECONDS);
			}
			
			if (!cookie.equals("")&&cookie.length()>0)
				get.setRequestHeader("Cookie", cookie);
			
			client.executeMethod(get);

			if (needAppendCookies) {
				client.getState().addCookies(client.getState().getCookies());
			}
			if (needResponse) {
				responseBody = get.getResponseBody();
				html = new String(responseBody, "GBK");
			}
			return html;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			log.error("processGet出错url="+url);
		} finally {
			get.releaseConnection();
		}
		return "";
	}	
	
	/**
	 * 带字符集编码方式无cookie以get方式获取数据
	 * 
	 * @param url
	 *            HTTP请求地址
	 * @param needResponse
	 *            是否需要返回结果
	 * @param responseCharsetName
	 *            返回结果编码集
	 * @return
	 */
	public static String getStringByUrl(String url, boolean needResponse,
			String responseCharsetName) {
		return processGet(getHttpClient(), null, url, needResponse, "", false,
				responseCharsetName);
	}

	/**
	 * 带字符集编码方式带cookie以get方式获取数据
	 * 
	 * @param url
	 *            HTTP请求地址
	 * @param needResponse
	 *            是否需要返回结果
	 * @param cookie
	 * @param responseCharsetName
	 *            返回结果编码集
	 * @return
	 */
	public static String getStringByUrlAndCookie(String url,
			boolean needResponse, String cookie, String responseCharsetName) {
		return processGet(getHttpClient(), null, url, needResponse, cookie,
				true, responseCharsetName);
	}

	public static String processGet(HttpClient client, GetMethod get,
			String url, boolean needResponse, String cookie,
			boolean needAppendCookies, String responseCharsetName) {
		byte[] responseBody = null;

		try {
			if (client == null || url == null || url.equals(""))
				return "";

			String html = "";

			if (get == null) {
				get = new GetMethod(url);
				get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT_READ);
			}

			if (!cookie.equals("") && cookie.length() > 0)
				get.setRequestHeader("Cookie", cookie);

			client.executeMethod(get);

			if (needAppendCookies) {
				client.getState().addCookies(client.getState().getCookies());
			}
			if (needResponse) {
				responseBody = get.getResponseBody();
				html = new String(responseBody, responseCharsetName);
			}
			return html;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("processGet出错url=" + url);
		} finally {
			get.releaseConnection();
		}
		return "";
	}

	/**
	 * 以POST方式提交HTTP请求
	 * @param url
	 *            HTTP地址
	 * @param needResponse
	 *            是否需要返回结果
	 * @param responseCharsetName
	 *            返回结果字符集
	 * @param params
	 *            提交的参数
	 * @return
	 */
	public static String postDataByUrl(String url, boolean needResponse,
			String responseCharsetName, Map<String, String> params) {
		return processPost(getHttpClient(), null, url, needResponse, "", false,
				responseCharsetName, params,null);
	}
	
	/**
	 * 请求及相应都采用utf8编码
	 * @param url
	 * @param needResponse
	 * @param params
	 * @return
	 */
    public static String postDataWithUTF8(String url, boolean needResponse,
            Map<String, String> params) {
        return processPost(getHttpClient(), null, url, needResponse, "", false, "utf-8", params,
                "utf-8");
    }

	/**
	 * 请求及相应都采用utf8编码
	 * @param url
	 * @param needResponse
	 * @param content
	 * @return
	 */
    public static String postDataWithUTF8(String url, boolean needResponse,
            String content) {
        return processPost(getHttpClient(), null, url, needResponse, "", false, "utf-8", content,
                "utf-8");
    }

	/**
	 * 提供postMethod自由设定选项 如可以自己设置content-type属性
	 */
	public static String postDataWithUTF8(PostMethod postMethod, String url, boolean needResponse, String content) {
		return processPost(getHttpClient(), postMethod, url, needResponse, "", false, "utf-8", content, "utf-8");
	}
    
	/**
	 * 以POST方式提交HTTP请求，保存cookie
	 * 
	 * @param url
	 *            HTTP地址
	 * @param needResponse
	 *            是否需要返回结果
	 * @param cookie
	 * @param responseCharsetName
	 *            返回结果字符集
	 * @param params
	 *            提交的参数
	 * @return
	 */
    public static String postDataByUrlAndCookie(String url, boolean needResponse, String cookie,
            String responseCharsetName, Map<String, String> params) {
        return processPost(getHttpClient(), null, url, needResponse, cookie, true,
                responseCharsetName, params, null);
    }

    public static String processPost(HttpClient client, PostMethod post, String url,
            boolean needResponse, String cookie, boolean needAppendCookies,
            String responseCharsetName, String content, String requestCharset) {
		byte[] responseBody = null;

		try {
			if (client == null || url == null || url.equals(""))
				return "";

			String html = "";

			if (post == null) {
				post = new PostMethod(url);
				post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT_READ);
				post.addRequestHeader("Content-Type","application/json;");
				
				if(requestCharset != null){
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
							requestCharset);
				}
			}

			if (!cookie.equals("") && cookie.length() > 0)
				post.setRequestHeader("Cookie", cookie);

			post.setRequestBody(content);

			client.executeMethod(post);

			if (needAppendCookies) {
				client.getState().addCookies(client.getState().getCookies());
			}

			if (needResponse) {
				responseBody = post.getResponseBody();
				html = new String(responseBody, responseCharsetName);
			}

			return html;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("processPost出错url=" + url);
		} finally {
			post.releaseConnection();
		}

		return "";    	
    }
    	
	/**
	 * 执行POST
	 * @param client
	 * @param post
	 * @param url
	 * @param needResponse
	 * @param cookie
	 * @param needAppendCookies
	 * @param responseCharsetName
	 * @param params
	 * @param requestCharset
	 * @return
	 */
    public static String processPost(HttpClient client, PostMethod post, String url,
            boolean needResponse, String cookie, boolean needAppendCookies,
            String responseCharsetName, Map<String, String> params, String requestCharset) {

		NameValuePair[] data = null;
		byte[] responseBody = null;

		try {
			if (client == null || url == null || url.equals(""))
				return "";

			String html = "";

			if (post == null) {
				post = new PostMethod(url);
				post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT_READ);
				if(requestCharset != null){
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
							requestCharset);
				}
			}

			if (!cookie.equals("") && cookie.length() > 0)
				post.setRequestHeader("Cookie", cookie);

			// 此处开始组装提交数据
			if (null != params && 0 != params.size()) {
				data = new NameValuePair[params.size()];
				int i = 0;
				for (String key : params.keySet()) {
					data[i] = new NameValuePair();
					data[i].setName(key);
					data[i].setValue(params.get(key));
					i++;
				}
			}

			post.setRequestBody(data);

			client.executeMethod(post);

			if (needAppendCookies) {
				client.getState().addCookies(client.getState().getCookies());
			}

			if (needResponse) {
				responseBody = post.getResponseBody();
				html = new String(responseBody, responseCharsetName);
			}

			return html;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("processPost出错url=" + url);
		} finally {
			post.releaseConnection();
		}

		return "";
	}
	
	/**
	 * 执行文件下载到指定文件
	 * 
	 * @param url
	 * @param cookie
	 * @param params
	 * @param fileUrl
	 */
	public static void getDownloadFile(String url, String cookie,
			Map<String, String> params, String fileUrl) {
		processDownload(getHttpClient(), null, url, cookie, true, params,
				fileUrl);
	}

	public static void processDownload(HttpClient client, PostMethod post,
			String url, String cookie, boolean needAppendCookies,
			Map<String, String> params, String fileUrl) {

		InputStream in = null;
		FileOutputStream out = null;
		byte[] buffer = new byte[BUFFER];

		NameValuePair[] data = null;
		try {
			if (client == null || url == null || url.equals("")
					|| fileUrl == null || fileUrl.equals(""))
				return;

			if (post == null) {
				post = new PostMethod(url);
				post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT_READ);
			}

			if (!cookie.equals("") && cookie.length() > 0)
				post.setRequestHeader("Cookie", cookie);

			// 此处开始组装提交数据
			if (null != params && 0 != params.size()) {
				data = new NameValuePair[params.size()];
				int i = 0;
				for (String key : params.keySet()) {
					data[i] = new NameValuePair();
					data[i].setName(key);
					data[i].setValue(params.get(key));
					i++;
				}
			}

			post.setRequestBody(data);

			client.executeMethod(post);

			if (needAppendCookies) {
				client.getState().addCookies(client.getState().getCookies());
			}

			in = post.getResponseBodyAsStream();
			out = new FileOutputStream(new File(fileUrl));
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("processDownload出错url=" + url);
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			post.releaseConnection();
		}

		return;
	}
	
	/**
	 * 登录系统
	 * 
	 * @param url
	 * @param cookie
	 * @param params
	 * @return
	 */
	public static String getLoginClientByUrlAndCookie(String url,
			String cookie, Map<String, String> params) {
		NameValuePair[] data = null;

		if (null != params && 0 != params.size()) {
			data = new NameValuePair[params.size()];
			int i = 0;
			for (String key : params.keySet()) {
				data[i] = new NameValuePair();
				data[i].setName(key);
				data[i].setValue(params.get(key));
				i++;
			}
		}

		return processLogin(getHttpClient(), null, url, cookie, data);
	}
	
	/**
	 * 登录系统，成功后返回cookie
	 * 
	 * @param client
	 * @param post
	 * @param url
	 * @param cookie
	 * @param data
	 * @return
	 */
	public static String processLogin(HttpClient client, PostMethod post,
			String url, String cookie, NameValuePair[] data) {

		try {
			if (client == null || url == null || url.equals(""))
				return null;

			if (post == null) {
				post = new PostMethod(url);
				post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler());
				post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT_READ);
			}

			// 设置cookie
			if (null != cookie && !"".equals(cookie) && 0 != cookie.length()) {
				post.setRequestHeader("Cookie", cookie);
			}

			// POST中添加提交的数据
			post.setRequestBody(data);

			// 执行提交
			client.executeMethod(post);

			// 登录成功
			if (post.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 返回cookie
				return client.getState().getCookies().toString();
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("getLoginClientByPost出错url=" + url);
		} finally {
			post.releaseConnection();
		}

		return null;
	}
	
	/**
	 * 上传指定路径的文件到指定URL，name为input标签的name属性
	 */
	public static String uploadFile(String url, String name, File file, Map<String, String> params) {
		try {
			ArrayList<Part> partArrayList = new ArrayList<>();
			partArrayList.add(new FilePart(name, file));
			for (String key : params.keySet()) {
				partArrayList.add(new StringPart(key, params.get(key)));
			}

			PostMethod post = new PostMethod(url);
			post.setRequestEntity(new MultipartRequestEntity(partArrayList.toArray(new Part[]{}), post.getParams()));

			getHttpClient().executeMethod(post);

			return post.getResponseBodyAsString();
		} catch (Exception e) {
			log.error("processUploadImg error:" + e.getMessage(), e);
		}

		return "";
	}
	
	/**
	 * 往指定的url发送一个xml格式的post请求
	 * @param httpUrl 请求地址
	 * @param xmlContent xml内容
	 * @return 返回请求状态码  如果请求异常 则返回  0
	 */
	public static int executeXmlPost(String httpUrl, String xmlContent) {
		PostMethod postMethod = new PostMethod(httpUrl);
		try {
			postMethod.setRequestEntity(new StringRequestEntity(xmlContent,"text/xml", "utf-8"));
			postMethod.setRequestHeader("Content-type", "text/xml; charset=utf-8");
			postMethod.addRequestHeader("User-Agent", "curl/7.12.1");
			HttpClient httpClient = new HttpClient();
			//设置连接 与 读取超时参数
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 60 * 2);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(1000 * 60 * 5);
			return httpClient.executeMethod(postMethod);
		} catch (UnsupportedEncodingException ex) {
			log.error(ex.getMessage(), ex);
			return 0;
		} catch (HttpException ex) {
			log.error(ex.getMessage(), ex);
			return 0;
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			return 0;
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
	 * 通过RequestBody传递请求参数，不使用名值对
	 * @param url
	 * @param needResponse
	 * @param responseCharsetName
	 * @param postData
	 * @return
	 */
	@SuppressWarnings("all")
    public static String postDataByRequestBody(String url, String postData,boolean needResponse,
            String responseCharsetName) {

        byte[] responseBody = null;
        HttpClient client = getHttpClient();
        PostMethod post = new PostMethod(url);
        try {
            String html = "";
            post.setRequestHeader("Content-type", "application/json; charset=utf-8");
            post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler());
            post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT_READ);
            post.setRequestBody(postData);
            client.executeMethod(post);
            if (needResponse) {
                responseBody = post.getResponseBody();
                if(StringUtils.isEmpty(responseCharsetName)){
                    responseCharsetName = "utf-8";
                }
                html = new String(responseBody, responseCharsetName);
            }
            return html;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("postDataByRequestBody出错,url=" + url+",postData"+postData);
        } finally {
            post.releaseConnection();
        }
        return "";
    }


}
