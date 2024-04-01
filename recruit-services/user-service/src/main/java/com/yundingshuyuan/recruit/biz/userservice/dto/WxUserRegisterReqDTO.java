package com.yundingshuyuan.recruit.biz.userservice.dto;

import com.yundingshuyuan.recruit.biz.userservice.dao.entity.WxUserInfoDO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@AutoMapper(target = WxUserInfoDO.class)
public class WxUserRegisterReqDTO {
    // 真实姓名

    Integer limits = -1;
    String openId;


}
