package com.yundingshuyuan.recruit.biz.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {
    private String appid;

    private String secret;

    private String token;

    private String aesKey;

    //@Value("${msgDataFormat}")
    private long msgDataFormat;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}