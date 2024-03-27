package com.yundingshuyuan.recruit.biz.userservice.controller;

import com.yundingshuyuan.framework.starter.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RecruitResult
@Deprecated
@Tag(name = "用户登录相关接口")
@RequestMapping
public class LoginController {

/*    @Autowired
    private LoginService loginService;

    @GetMapping("/api/user-service/login")
    @SaCheckLogin
    @Operation(summary = "用户登录")
    public Result<> login(String username, String password) {
        loginService.login(username, password);
        log.info("用户身份：{}", StpUtil.getRoleList());
        log.info("用户权限：{}", StpUtil.getPermissionList());
        String result = "用户身份：" + StpUtil.getRoleList() + "用户权限：" + StpUtil.getPermissionList();
        if (loginService.isLogin()) {
            return BasicResultVO.success("登录成功", result);
        }
        return BasicResultVO.fail("登录失败");
    }

    *//**
     * 通过 Token 检查用户是否登录
     *//*
    @GetMapping("/api/user-service/check-login")
    @Operation(summary = "用户登出")
    public Result<UserLoginRespDTO> checkLogin(@RequestParam("accessToken") String accessToken) {
        UserLoginRespDTO result = userLoginService.checkLogin(accessToken);
        return Results.success(result);
    }

    @GetMapping("/api/user-service/logout")
    @Operation(summary = "用户登出")
    public Result<Void> logout() {
        log.info("用户：{} 退出", StpUtil.getLoginId());
        if (!loginService.isLogin()) {
            return BasicResultVO.fail("用户未登录");
        }
        StpUtil.logout(StpUtil.getLoginId());
        return BasicResultVO.success("退出成功");

    }*/
}
