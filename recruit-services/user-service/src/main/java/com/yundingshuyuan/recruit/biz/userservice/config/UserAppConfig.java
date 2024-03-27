package com.yundingshuyuan.recruit.biz.userservice.config;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig
public class UserAppConfig {
    @Bean
    public WxMaProperties wxMaProperties() {
        return new WxMaProperties();
    }
}
