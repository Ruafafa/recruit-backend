package com.yundingshuyuan.recruit.biz.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateReqDTO {
    // 真实姓名
    String name;
    // 性别
    String gender;
    // 学号
    String studentId;
    // 报名方向
    String direction;
    // 电话
    String phone;
    // 邮箱 (可选)
    String email;
    // 专业班级
    String majorClass;
}
