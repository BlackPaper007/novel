package com.config;

import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Slf4j
@Configuration
public class DruidConfig {


    @Bean(destroyMethod = "close",initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @ConditionalOnMissingBean
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setBreakAfterAcquireFailure(true);
        dataSource.setConnectionErrorRetryAttempts(0);
        log.info("************************************************************");
        log.info("*                                                          *");
        log.info("*====["+dataSource.getName()+"]===== Druid连接池已启动 =====*");
        log.info("*               http://localhost/novel/druid               *");
        log.info("************************************************************");
        return dataSource;
    }


    /**
     * 注册Filter，监控拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
