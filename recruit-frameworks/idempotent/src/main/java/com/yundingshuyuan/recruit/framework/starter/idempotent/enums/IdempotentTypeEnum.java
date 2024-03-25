package com.yundingshuyuan.recruit.framework.starter.idempotent.enums;

/**
 * 幂等验证类型枚举
 */
public enum IdempotentTypeEnum {

    /**
     * 基于 Token 方式验证 (推荐)
     * <ul>适用于多线程、分布式环境</ul>
     */
    TOKEN,

    /**
     * 基于 SpEL 表达式方式验证
     */
    SPEL
}
