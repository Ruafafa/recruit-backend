package com.yundingshuyuan.recruit.biz.gatewayservice.filiter;

import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {
    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    //  =============================  拦截器  ===============================
    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public SaResult handlerException(NotLoginException e) {
        // 打印堆栈，以供调试
        e.printStackTrace();
        // 返回给前端
        return SaResult.error(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public SaResult handlerException(NotPermissionException e) {
        log.error("未登录异常", e);
        return SaResult.error("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public SaResult handlerException(NotRoleException e) {
        log.error("缺少角色异常", e);
        return SaResult.error("缺少角色：" + e.getRole());
    }

    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public SaResult handlerException(NotSafeException e) {
        log.error("二级认证校验失败异常", e);
        return SaResult.error("二级认证校验失败：" + e.getService());
    }


    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotBasicAuthException.class)
    public SaResult handlerException(NotBasicAuthException e) {
        log.error("Http Basic 校验失败异常", e);
        return SaResult.error(e.getMessage());
    }

    // 拦截：其它所有异常
    @ExceptionHandler(Exception.class)
    public SaResult handlerException(Exception e) {
        log.error("发生异常", e);
        return SaResult.error(e.getMessage());
    }


    //  =============================  开启注解鉴权  ===============================

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能，然后去Controller层加上对应的注解就可以了
     *
     * @param registry
     */
    @Override
    @Transactional
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                    // 角色校验 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
                    SaRouter.match("/api/user-service/info/**", "/api/user-service/info/update", r -> StpUtil.checkRoleOr("user", "admin"));

                }))
                .addPathPatterns("/**")
                //开放 knife4j 接口文档路径
                .excludePathPatterns("/api/user-service/wxminiapp/login", "/api/user-service/wxminiapp/test-login")
                .excludePathPatterns("/doc.html", "/webjars/**", "/swagger-resources/**", "/v3/**");
    }
}
