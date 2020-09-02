package com.impl.abs;


import java.io.IOException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.config.ThreadConfig;
import com.entity.Result;
import com.Enum.Site;
import com.utlis.ChapterSpiderUtil;
import com.config.Config;


/**
 * 开始爬虫任务
 *
 * @author smile
 */
public abstract class AbstractSpider {

    String HeaderK = "user-agent";
    String HeaderV = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 "
            + "(KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";

    protected ThreadConfig config;

    private static final int timeout = 60000;

    private String regex = "^5[0-9][0-9]$";

    protected static final Logger log = LoggerFactory.getLogger(AbstractSpider.class);

    private RequestConfig c = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD)
            .setSocketTimeout(timeout)
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .build();


    public Result crawl(String url) {
        String content = null;
        HttpGet httpGet = new HttpGet(url);
        /*HttpHost proxy=new HttpHost("125.110.85.23",9000); */
        httpGet.setConfig(c);
        httpGet.setHeader(HeaderK, HeaderV);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("x-forwarded-for", ChapterSpiderUtil.getIPProxy());
        Result result = new Result();
        log.info("------开始抓取页面-----，链接：" + url);
        try {
            for (int i = 0; i < config.getTries(); i++) {
                try (CloseableHttpClient httpClient = buildSSLCloseableHttpClient();
                     CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                    result.setCode(httpResponse.getStatusLine().getStatusCode());
                    content = EntityUtils.toString(httpResponse.getEntity(), Config.
                            getContext(Site.getEnumByUrl(url)).get("charset"));
                    result.setDate(content);
                    if (String.valueOf(result.getCode()).matches(regex))
                        throw new IOException();
                    break;
                } catch (IOException e) {
                    log.info("响应代码：" + result.getCode());
                    log.info("链接：" + url);
                    log.info("尝试第[" + (i + 1) + "/" + config.getTries() + "]次抓取失败了！");
                    Thread.sleep(config.getSleepTime());
                    if ((i+1)==config.getTries()){
                        result.setMessage("false");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 根据文档使用自定义的org.apache.http.conn.ssl.TrustStrategy绕过标准的信任验证过程
     * 绕过htts证书
     * 解决异常：javax.net.ssl.SSLHandshakeException:
     * sun.security.validator.ValidatorException:
     * PKIX path building failed:
     * sun.security.provider.certpath.SunCertPathBuilderException:
     * unable to find valid certification path to requested target
     *
     * @return
     * @throws Exception
     */
    private static CloseableHttpClient buildSSLCloseableHttpClient()
            throws Exception {
        // 信任所有
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null,
                        (TrustStrategy) (chain, authType) -> true).build();

        HttpClientBuilder cb = HttpClientBuilder
                .create()
                .disableAutomaticRetries()
                //.setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setRetryHandler(new DefaultHttpRequestRetryHandler(10, true))
                .setDefaultRequestConfig(
                        RequestConfig.custom().setConnectTimeout(timeout)
                                .setSocketTimeout(timeout).build());


        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[]{"TLSv1"}, null,
                new NoopHostnameVerifier());
        // new NoopHostnameVerifier():这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        return cb.setSSLSocketFactory(sslsf).build();
    }
}
