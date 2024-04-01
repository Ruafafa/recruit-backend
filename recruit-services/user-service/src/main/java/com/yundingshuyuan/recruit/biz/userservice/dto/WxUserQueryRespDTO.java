package com.yundingshuyuan.recruit.biz.userservice.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yundingshuyuan.recruit.biz.userservice.dao.entity.WxUserInfoDO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@AutoMapper(target = WxUserInfoDO.class)
public class WxUserQueryRespDTO {
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
    @AutoMapping(target = "limits")
    private String limits;


}
