package com.yundingshuyuan.recruit.biz.userservice.common.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    GUEST("guest", -1),
    USER("user", 0),
    ADMIN("admin", 1),

    ;


    private final String roleName;
    private final Integer roleCode;


    RoleEnum(String roleName, Integer roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

}
