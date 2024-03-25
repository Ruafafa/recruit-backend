package com.yundingshuyuan.framework.starter.convention.exception;


import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatus;
import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatusEnum;

/**
 * 远程服务调用异常
 */
public class RemoteException extends AbstractException {

    public RemoteException(String message) {
        this(message, null, RespStatusEnum.REMOTE_ERROR);
    }

    public RemoteException(String message, RespStatus errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, RespStatus errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
