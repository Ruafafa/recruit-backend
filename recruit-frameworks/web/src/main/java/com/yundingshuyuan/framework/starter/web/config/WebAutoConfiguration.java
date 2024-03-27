package com.yundingshuyuan.framework.starter.web.config;

import com.yundingshuyuan.framework.starter.web.advice.RecruitGlobalExceptionHandlerAdvice;
import com.yundingshuyuan.framework.starter.web.advice.RecruitResponseBodyAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web 组件自动装配
 */
@Configuration
public class WebAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RecruitGlobalExceptionHandlerAdvice globalExceptionHandler() {
        return new RecruitGlobalExceptionHandlerAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    public RecruitResponseBodyAdvice recruitResponseBodyAdvice() {
        return new RecruitResponseBodyAdvice();
    }

}
