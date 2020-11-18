package com.seasky.sytest.druid;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {
    /**
     * 主要实现WEB监控的配置处理
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        // 现在要进行druid监控的配置处理操作
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new StatViewServlet(), "/druid/*");
        // 控制台管理用户名
        servletRegistrationBean.addInitParameter("loginUsername", "1");
        // 控制台管理密码
        servletRegistrationBean.addInitParameter("loginPassword", "2");
        return servletRegistrationBean;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.filter.stat")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.filter.stat", name = "enabled")
    @ConditionalOnMissingBean
    public StatFilter statFilter() {
        return new StatFilter();
    }
}
