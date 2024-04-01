package com.yundingshuyuan.recruit.biz.userservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.yundingshuyuan.framework.starter.convention.result.Result;
import com.yundingshuyuan.framework.starter.web.Results;
import com.yundingshuyuan.framework.starter.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.biz.userservice.dto.UserDeletionReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserQueryRespDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserUpdateReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.service.WxUserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Slf4j
@Tag(name = "用户信息接口")
@RequiredArgsConstructor
@RecruitResult
@RestController
@RequestMapping("/api/user-service/info")
public class WxUserInfoController {

    private final WxUserInfoService wuiService;

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("/query")
    public Result<WxUserQueryRespDTO> queryUserInfo() {
        Object loginId = StpUtil.getLoginId();
        WxUserQueryRespDTO result = wuiService.queryUserInfo(String.valueOf(loginId));
        return Results.success(result);
    }

    /**
     * 检查用户名是否已存在
     */
    @GetMapping("/has-user")
    public Result<Boolean> hasUsername(@RequestParam("openId") @NotEmpty String openId) {
        return Results.success(wuiService.hasUser(openId));
    }


    /**
     * 修改用户
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody @Valid WxUserUpdateReqDTO requestParam) {
        int update = wuiService.update(requestParam);
        return Results.success("修改行数:" + update);
    }

    /**
     * 注销用户
     */
    @PostMapping("/deletion")
    public Result<Void> deletion(@RequestBody @Valid UserDeletionReqDTO requestParam) {
        wuiService.deletion(requestParam);
        return Results.success();
    }


    @GetMapping("/test-login")
    public Result<Void> testLogin() {
        return Results.success();
    }

}
