package com.yundingshuyuan.framework.starter.convention.exception;

import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatus;
import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatusEnum;

/**
 * 客户端异常
 */
public class ClientException extends AbstractException {

    public ClientException(RespStatus errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, RespStatusEnum.CLIENT_BAD_PARAMETERS);
    }

    public ClientException(String message, RespStatus errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, RespStatus errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
