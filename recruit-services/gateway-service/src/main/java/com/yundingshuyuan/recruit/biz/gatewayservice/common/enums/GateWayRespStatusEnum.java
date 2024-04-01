package com.yundingshuyuan.recruit.biz.gatewayservice.common.enums;

import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatus;

public enum GateWayRespStatusEnum implements RespStatus {

    GATEWAY_UNKNOWN_ERROR("B001001", "网关未知错误"),
    UNAUTHORIZED("B001002", "未授权"),
    ;

    private String code;
    private String message;

    GateWayRespStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
