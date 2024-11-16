package com.sparta.harmony.common.config;

import com.sparta.harmony.common.logging.filter.ReqResLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingFilterConfig {
    @Bean
    public FilterRegistrationBean<ReqResLoggingFilter> loggingFilter() {
        FilterRegistrationBean<ReqResLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ReqResLoggingFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 URL 패턴에 적용

        return registrationBean;
    }
}
