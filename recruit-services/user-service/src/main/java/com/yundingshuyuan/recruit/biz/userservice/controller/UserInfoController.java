package com.yundingshuyuan.recruit.biz.userservice.controller;


import com.yundingshuyuan.framework.starter.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RecruitResult
@Tag(name = "用户信息接口")
@Slf4j
@RequestMapping("/user")
public class UserInfoController {

}
