package com.yundingshuyuan.recruit.biz.userservice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.recruit.biz.userservice.common.enums.RoleEnum;
import com.yundingshuyuan.recruit.biz.userservice.dao.entity.WxUserInfoDO;
import com.yundingshuyuan.recruit.biz.userservice.dao.mapper.WxUserInfoMapper;
import com.yundingshuyuan.recruit.biz.userservice.dto.UserDeletionReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserQueryRespDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserRegisterReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.dto.WxUserUpdateReqDTO;
import com.yundingshuyuan.recruit.biz.userservice.service.WxUserInfoService;
import com.yundingshuyuan.recruit.framework.starter.common.utils.RedisUtils;
import io.github.linpeilie.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class WxUserInfoServiceImpl implements WxUserInfoService {

    private final WxUserInfoMapper wuiMapper;
    private final Converter converter;
    private final RedisUtils redisUtils;

    @Override
    public int update(WxUserUpdateReqDTO requestParam) {
        String openId = String.valueOf(StpUtil.getLoginId());

        WxUserInfoDO updateData = converter.convert(requestParam, WxUserInfoDO.class);
        updateData.setOpenId(openId);

        // 校验用户是否为游客，如果是，完善信息后升级为普通用户
        if (isGuest(updateData.getOpenId())) {
            updateData.setLimits(1);
            // 缓存同步
            Object val = redisUtils.hget("user_limits", openId);
            if (val != null) {
                redisUtils.hset("user_limits", openId, RoleEnum.USER.getRoleCode(), 3600000L);
            }
        }

        int updateLine = wuiMapper.update(updateData, new QueryWrapper<WxUserInfoDO>().eq("open_id", openId));
        return updateLine;
    }

    @Override
    public int deletion(UserDeletionReqDTO requestParam) {
        return 0;
    }

    @Override
    public WxUserQueryRespDTO queryUserInfo(String openId) {

        WxUserInfoDO userInfo = wuiMapper.selectOne(new QueryWrapper<WxUserInfoDO>().eq("open_id", openId));
        if (userInfo == null) return null;
        WxUserQueryRespDTO result = converter.convert(userInfo, WxUserQueryRespDTO.class);

        // 展示账户状态
        RoleEnum[] values = RoleEnum.values();
        String limitsName = values[userInfo.getLimits() + 1].getRoleName();
        result.setLimits(limitsName);
        return result;
    }

    @Override
    public Integer register(WxUserRegisterReqDTO requestParam) {
        WxUserInfoDO.WxUserInfoDOBuilder builder = WxUserInfoDO.builder();
        // 构建
        String openId = String.valueOf(StpUtil.getLoginId());
        builder.openId(openId)
                .limits(requestParam.getLimits())
                .name("默认用户" + IdUtil.fastSimpleUUID().substring(0, 4));
        WxUserInfoDO insertData = builder.build();
        return wuiMapper.insert(insertData);
    }

    /**
     * 判断用户是否存在
     *
     * @param openId
     * @return
     */
    @Override
    public boolean hasUser(String openId) {
        WxUserQueryRespDTO wxUserQueryRespDTO = this.queryUserInfo(openId);
        return !ObjectUtil.isEmpty(wxUserQueryRespDTO);
    }

    /**
     * 判断用户是否完善了信息
     *
     * @param openId
     * @return
     */
    @Override
    public boolean isGuest(String openId) {
        WxUserInfoDO userInfo = wuiMapper.selectOne(new QueryWrapper<WxUserInfoDO>().eq("open_id", openId));
        Integer limits = userInfo.getLimits();
        return !(limits != null && limits != -1);
    }
}
