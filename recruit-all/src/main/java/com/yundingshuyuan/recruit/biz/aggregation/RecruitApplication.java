package com.yundingshuyuan.recruit.biz.aggregation;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableKnife4j
@SpringBootApplication(scanBasePackages = {
        "com.yundingshuyuan.recruit.biz.userservice",
        "com.yundingshuyuan.recruit.biz.gatewayservice"
})
public class RecruitApplication {
    public static void main(String[] args) {
        System.setProperty("apollo.configService", "http://localhost:8080");
        try {
            SpringApplication.run(RecruitApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
