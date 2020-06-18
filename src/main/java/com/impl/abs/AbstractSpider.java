<<<<<<< HEAD
package com.impl.abs;



import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novelEnum.Site;
import com.utlis.Config;

/**
 * 开始爬虫任务
 * @author smile
 *
 */
public abstract class AbstractSpider{
	
	String HeaderK = "user-agent";
	String HeaderV = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 "
			+ "(KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";
	
	private RequestConfig config = RequestConfig.custom()
			.setConnectTimeout(50000)
			.setConnectionRequestTimeout(50000)
			.build(); 
	
	static Logger log = LoggerFactory.getLogger(AbstractSpider.class);
	static {
		log.info("------开始抓取网页内容-------");
		
	}
	
	protected String crawl(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		/*HttpHost proxy=new HttpHost("125.110.85.23",9000); */
		httpGet.setConfig(config);
		httpGet.setHeader(HeaderK,HeaderV);
		try (CloseableHttpClient httpClient = buildSSLCloseableHttpClient();
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet)){
			String content = EntityUtils.toString(httpResponse.getEntity(),Config.
				getContext(Site.getEnumByUrl(url)).get("charset"));
			return content;
		} catch (Exception e) {
			log.warn("--------网络请求出错--------",e);
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 根据文档使用自定义的org.apache.http.conn.ssl.TrustStrategy绕过标准的信任验证过程
	 * 绕过htts证书
	 * 解决异常：javax.net.ssl.SSLHandshakeException: 
	 * sun.security.validator.ValidatorException: 
	 * PKIX path building failed: 
	 * sun.security.provider.certpath.SunCertPathBuilderException: 
	 * unable to find valid certification path to requested target
	 * @return
	 * @throws Exception
	 */
	private static CloseableHttpClient buildSSLCloseableHttpClient()
			throws Exception {
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null,
				new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext, new String[] { "TLSv1" }, null,
                new NoopHostnameVerifier());
		// new NoopHostnameVerifier():这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
}
=======
package com.impl.abs;



import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
>>>>>>> second commit

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
=======
import com.entity.Message;
import com.entity.Novel;
import com.entity.ThreadConfig;
>>>>>>> second commit
import com.novelEnum.Site;
import com.utlis.Config;

/**
 * 开始爬虫任务
 * @author smile
 *
 */
public abstract class AbstractSpider{
	
<<<<<<< HEAD
	String HeaderK = "user-agent";
	String HeaderV = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 "
			+ "(KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";
	
	private RequestConfig config = RequestConfig.custom()
=======
	protected List<Future<Novel>> tasks = new ArrayList<>();
	
	protected List<Novel> novels = new ArrayList<>();
	
	@SuppressWarnings("rawtypes")
	protected ThreadConfig config = new ThreadConfig();
	
	protected Message http = new Message();

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
	
	private String HeaderK = "user-agent";
	private String HeaderV = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 "
			+ "(KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";
	
	private RequestConfig c = RequestConfig.custom()
>>>>>>> second commit
			.setConnectTimeout(50000)
			.setConnectionRequestTimeout(50000)
			.build(); 
	
<<<<<<< HEAD
	static Logger log = LoggerFactory.getLogger(AbstractSpider.class);
	static {
		log.info("------开始抓取网页内容-------");
		
	}
	
	protected String crawl(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		/*HttpHost proxy=new HttpHost("125.110.85.23",9000); */
		httpGet.setConfig(config);
		httpGet.setHeader(HeaderK,HeaderV);
		try (CloseableHttpClient httpClient = buildSSLCloseableHttpClient();
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet)){
			String content = EntityUtils.toString(httpResponse.getEntity(),Config.
				getContext(Site.getEnumByUrl(url)).get("charset"));
			return content;
		} catch (Exception e) {
			log.warn("--------网络请求出错--------",e);
			throw new RuntimeException(e);
=======
	protected String crawl(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		/*HttpHost proxy=new HttpHost("125.110.85.23",9000); */
		httpGet.setConfig(c);
		httpGet.setHeader(HeaderK,HeaderV);
		log.info("------开始抓取页面-----，链接："+url);
		try (CloseableHttpClient httpClient = buildSSLCloseableHttpClient();
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet)){
			http.setCode(httpResponse.getStatusLine().getStatusCode());
			String content = EntityUtils.toString(httpResponse.getEntity(),Config.
				getContext(Site.getEnumByUrl(url)).get("charset"));
			http.setMessage(content);
			return content;
		} catch (Exception e) {
			log.warn("----网络出现异常-----状态："+http.getCode()==null?"请求站点失效!!!":http.getCode()+"",e);
			throw new RuntimeException();
>>>>>>> second commit
		} 
	}
	
	/**
	 * 根据文档使用自定义的org.apache.http.conn.ssl.TrustStrategy绕过标准的信任验证过程
	 * 绕过htts证书
	 * 解决异常：javax.net.ssl.SSLHandshakeException: 
	 * sun.security.validator.ValidatorException: 
	 * PKIX path building failed: 
	 * sun.security.provider.certpath.SunCertPathBuilderException: 
	 * unable to find valid certification path to requested target
	 * @return
	 * @throws Exception
	 */
	private static CloseableHttpClient buildSSLCloseableHttpClient()
			throws Exception {
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null,
				new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext, new String[] { "TLSv1" }, null,
                new NoopHostnameVerifier());
		// new NoopHostnameVerifier():这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
}
>>>>>>> second commit
