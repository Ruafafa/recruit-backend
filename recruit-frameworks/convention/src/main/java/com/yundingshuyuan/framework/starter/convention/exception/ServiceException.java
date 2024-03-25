package com.yundingshuyuan.framework.starter.convention.exception;

import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatus;
import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatusEnum;

import java.util.Optional;

/**
 * 服务端异常
 */
public class ServiceException extends AbstractException {

    public ServiceException(String message) {
        this(message, null, RespStatusEnum.SERVICE_ERROR);
    }

    public ServiceException(RespStatus errorCode) {
        this(null, errorCode);
    }

    public ServiceException(String message, RespStatus errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, RespStatus errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message()), throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
