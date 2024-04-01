package com.yundingshuyuan.recruit.biz.gatewayservice.config;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.recruit.biz.userservice.common.enums.RoleEnum;
import com.yundingshuyuan.recruit.biz.userservice.dao.entity.WxUserInfoDO;
import com.yundingshuyuan.recruit.biz.userservice.dao.mapper.WxUserInfoMapper;
import com.yundingshuyuan.recruit.framework.starter.common.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@AllArgsConstructor
@Component
public class StpInterfaceImpl implements StpInterface {

    private final WxUserInfoMapper wuiMapper;

    private final RedisUtils redisUtils;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String openId = String.valueOf(loginId);
        ArrayList<String> roles = new ArrayList<>();
        Integer limits;

        // 优先通过缓存获取用户权限,否则查询数据库
        Object val = redisUtils.hget("user_limits", openId);
        if (val == null) {
            limits = wuiMapper.selectOne(new QueryWrapper<WxUserInfoDO>().eq("open_id", loginId)).getLimits();
            redisUtils.hset("user_limits", openId, limits, 3600000L);
        } else {
            limits = (Integer) val;
        }

        // 根据权限返回角色
        RoleEnum[] values = RoleEnum.values();
        String roleName = values[limits + 1].getRoleName();
        roles.add(roleName);
        return roles;
    }
}
