package com.yundingshuyuan.framework.starter.web.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yundingshuyuan.framework.starter.web.Results;
import com.yundingshuyuan.framework.starter.web.annotation.RecruitResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 统一返回结构
 */
@Slf4j
@ControllerAdvice
public class RecruitResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final String RETURN_CLASS = "Result";

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getContainingClass().isAnnotationPresent(RecruitResult.class) || methodParameter.hasMethodAnnotation(RecruitResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 对于返回 String 类型时，StringHttpMessageConverter 会将其转换为 JSON 字符串，特此处理
        Class<?> parameterType = methodParameter.getParameterType();
        if (parameterType.equals(String.class)) {
            return JSON.toJSONString(Results.success(data), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }
        if (Objects.nonNull(data) && Objects.nonNull(data.getClass())) {
            String simpleName = data.getClass().getSimpleName();
            if (RETURN_CLASS.equalsIgnoreCase(simpleName)) {
                return data;
            }
        }
        return Results.success(data);
    }
}
