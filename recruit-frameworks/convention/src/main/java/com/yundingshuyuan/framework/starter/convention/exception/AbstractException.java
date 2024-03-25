package com.yundingshuyuan.framework.starter.convention.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatus;
import lombok.Getter;

import java.util.Optional;

/**
 * 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 *
 * @see ClientException
 * @see ServiceException
 * @see RemoteException
 */
@Getter
public abstract class AbstractException extends RuntimeException {

    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, RespStatus errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(CharSequenceUtil.isNotBlank(message) ? message : null).orElse(errorCode.message());
    }
}
