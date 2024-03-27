package com.yundingshuyuan.recruit.biz.userservice.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.StpUtil;
import com.yundingshuyuan.framework.starter.convention.result.Result;
import com.yundingshuyuan.framework.starter.web.Results;
import com.yundingshuyuan.framework.starter.web.annotation.RecruitResult;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxMiniAppUserLoginRespDTO;
import com.yundingshuyuan.recruit.framework.starter.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RecruitResult
@RestController
@RequestMapping("/api/user-service/wxminiapp")
public class WxLoginController {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private SpringUtils springUtils;

    /**
     * 微信小程序登录并生成Token
     *
     * @param code 小程序端调用 wx.login()向微信接口服务获取 临时登录凭证code
     * @return
     */
    @GetMapping("/login")
    public Result<WxMiniAppUserLoginRespDTO> wxMiniUserApplogin(String code) throws WxErrorException {
        WxMaJscode2SessionResult session;
        session = wxMaService.getUserService().getSessionInfo(code);
        // 换取用户唯一标识
        String openid = session.getOpenid();
        // 使用 openid 登录
        StpUtil.login(openid);
        // 返回 token + openid
        String tokenValue = StpUtil.getTokenValue();
        WxMiniAppUserLoginRespDTO wxaRespDTO = new WxMiniAppUserLoginRespDTO(tokenValue, openid);
        return Results.success(wxaRespDTO);
    }

    @GetMapping("/apollo")
    public String testApollo() {
        return "yes";
    }
}
