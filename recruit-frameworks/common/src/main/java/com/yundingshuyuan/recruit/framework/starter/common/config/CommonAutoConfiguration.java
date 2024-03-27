package com.yundingshuyuan.recruit.framework.starter.common.config;

import com.yundingshuyuan.recruit.framework.starter.common.utils.OkHttpUtils;
import com.yundingshuyuan.recruit.framework.starter.common.utils.RedisUtils;
import com.yundingshuyuan.recruit.framework.starter.common.utils.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public OkHttpUtils okHttpUtils() {
        return new OkHttpUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }
}
