package com.yundingshuyuan.recruit.biz.userservice.service;

import com.yundingshuyuan.recruit.biz.userservice.dto.UserDeletionReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserQueryRespDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserRegisterReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserUpdateReqDTO;

public interface WxUserInfoService {
    int update(WxUserUpdateReqDTO requestParam);

    int deletion(UserDeletionReqDTO requestParam);

    WxUserQueryRespDTO queryUserInfo(String openId);

    Integer register(WxUserRegisterReqDTO requestParam);

    boolean hasUser(String username);

    boolean isGuest(String openId);
}
