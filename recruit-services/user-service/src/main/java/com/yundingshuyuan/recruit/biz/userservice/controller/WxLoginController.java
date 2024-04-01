package com.yundingshuyuan.recruit.biz.userservice.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.StpUtil;
import com.yundingshuyuan.framework.starter.convention.result.Result;
import com.yundingshuyuan.framework.starter.web.Results;
import com.yundingshuyuan.framework.starter.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxMiniAppUserLoginRespDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserRegisterReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.service.WxUserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RecruitResult
@RestController
@Tag(name = "微信用户登录接口")
@RequestMapping("/api/user-service/wxminiapp")
@RequiredArgsConstructor
public class WxLoginController {

    private final WxMaService wxMaService;
    private final WxUserInfoService wxUserInfoService;

    /**
     * 微信小程序登录并生成Token
     *
     * @param code 小程序端调用 wx.login()向微信接口服务获取 临时登录凭证code
     * @return
     */
    @GetMapping("/login")
    public WxMiniAppUserLoginRespDTO wxMiniUserApplogin(String code) throws WxErrorException {
        WxMaJscode2SessionResult session;
        session = wxMaService.getUserService().getSessionInfo(code);
        // 换取用户唯一标识
        String openid = session.getOpenid();
        // 使用 openid 登录
        StpUtil.login(openid);
        // 自动注册用户
        autoRegister(openid);
        // 返回 token + openid
        String tokenValue = StpUtil.getTokenValue();
        WxMiniAppUserLoginRespDTO wxaRespDTO = new WxMiniAppUserLoginRespDTO(tokenValue);
        return wxaRespDTO;
    }

    @GetMapping("/logout")
    public Result<String> logout() {
        StpUtil.logout();
        return Results.success("退出登录成功");
    }


    /**
     * 登录测试
     *
     * @return
     */
    @GetMapping("/test-login")
    public Result<WxMiniAppUserLoginRespDTO> testLogin(String openid) {
        // 使用 openid 登录
        StpUtil.login(openid, "phone");
        // 自动注册用户
        autoRegister(openid);
        // 返回 token + openid
        String tokenValue = StpUtil.getTokenValue();
        WxMiniAppUserLoginRespDTO wxaRespDTO = new WxMiniAppUserLoginRespDTO(tokenValue);
        return Results.success(wxaRespDTO);
    }


    /**
     * 自动注册用户
     *
     * @param openid
     */
    public void autoRegister(String openid) {
        boolean isExistUser = wxUserInfoService.hasUser(openid);
        if (!isExistUser) {
            WxUserRegisterReqDTO defaultUserInfo = new WxUserRegisterReqDTO(-1, openid);
            // 注册默认用户 (游客: limits =- 1)
            wxUserInfoService.register(defaultUserInfo);
        }
    }

}
