package com.yundingshuyuan.recruit.biz.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.yundingshuyuan.recruit.framework.starter.database.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName("user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxUserInfoDO extends BaseDO {

    @TableId
    private Integer id;
    @TableField("open_id")
    private String openId;
    private String name;
    private String gender;
    private String phone;
    private String email;
    @TableField("student_number")
    private String studentNumber;
    private String qq;
    private String major;
    // 书院ID
    @TableField("academy_id")
    private Integer academyId;
    // 用户权限
    private Integer limits;
    @Version
    private Integer version;
}
