package com.yundingshuyuan.recruit.framework.starter.idempotent.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 幂等自动装配
 */
@EnableConfigurationProperties(IdempotentProperties.class)
public class IdempotentAutoConfiguration {
//
//    /**
//     * 幂等切面
//     */
//    @Bean
//    public IdempotentAspect idempotentAspect() {
//        return new IdempotentAspect();
//    }
//
//    /**
//     * Token 方式幂等实现，基于 RestAPI 场景
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public IdempotentTokenService idempotentTokenExecuteHandler(DistributedCache distributedCache,
//                                                                IdempotentProperties idempotentProperties) {
//        return new IdempotentTokenByRestAPIExecuteHandler(distributedCache, idempotentProperties);
//    }
//
//    /**
//     * 申请幂等 Token 控制器，基于 RestAPI 场景
//     */
//    @Bean
//    public IdempotentTokenController idempotentTokenController(IdempotentTokenService idempotentTokenService) {
//        return new IdempotentTokenController(idempotentTokenService);
//    }
//
//    /**
//     * SpEL 方式幂等实现，基于 RestAPI 场景
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public IdempotentSpELService idempotentSpELByRestAPIExecuteHandler(RedissonClient redissonClient) {
//        return new IdempotentSpELByRestAPIExecuteHandler(redissonClient);
//    }
//
//    /**
//     * SpEL 方式幂等实现，基于 MQ 场景
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public IdempotentSpELByMQExecuteHandler idempotentSpELByMQExecuteHandler(DistributedCache distributedCache) {
//        return new IdempotentSpELByMQExecuteHandler(distributedCache);
//    }
}
