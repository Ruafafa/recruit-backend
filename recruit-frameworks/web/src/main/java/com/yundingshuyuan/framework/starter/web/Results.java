package com.yundingshuyuan.framework.starter.web;

import com.yundingshuyuan.framework.starter.convention.exception.AbstractException;
import com.yundingshuyuan.framework.starter.convention.respstatus.RespStatusEnum;
import com.yundingshuyuan.framework.starter.convention.result.Result;

import java.util.Optional;

/**
 * 全局返回对象构造器
 */
public final class Results {

    /**
     * 构造成功响应
     */
    public static Result<Void> success() {
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }

    /**
     * 构造带返回数据的成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(Result.SUCCESS_CODE)
                .setData(data);
    }

    /**
     * 构建服务端失败响应
     */
    public static Result<Void> failure() {
        return new Result<Void>()
                .setCode(RespStatusEnum.SERVICE_ERROR.code())
                .setMessage(RespStatusEnum.SERVICE_ERROR.message());
    }

    /**
     * 通过 {@link AbstractException} 构建失败响应
     */
    public static Result<Void> failure(AbstractException abstractException) {
        String errorCode = Optional.ofNullable(abstractException.getErrorCode())
                .orElse(RespStatusEnum.SERVICE_ERROR.code());
        String errorMessage = Optional.ofNullable(abstractException.getErrorMessage())
                .orElse(RespStatusEnum.SERVICE_ERROR.message());
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }

    /**
     * 通过 errorCode、errorMessage 构建失败响应
     */
    public static Result<Void> failure(String errorCode, String errorMessage) {
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }

    /**
     * 通过 {@link Throwable} 构建失败响应
     */
    public static Result<Void> failure(Throwable throwable) {
        return new Result<Void>()
                .setCode(RespStatusEnum.SERVICE_ERROR.code())
                .setMessage(throwable.getMessage());
    }
}