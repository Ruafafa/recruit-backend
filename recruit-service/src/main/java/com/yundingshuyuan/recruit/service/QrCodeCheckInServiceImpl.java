package com.yundingshuyuan.recruit.service;

import com.alibaba.fastjson.JSON;
import com.yundingshuyuan.recruit.api.QrCodeCheckInService;
import com.yundingshuyuan.recruit.dao.QrCodeCheckInMapper;
import com.yundingshuyuan.recruit.domain.CheckInEvent;
import com.yundingshuyuan.recruit.domain.vo.CheckInEventVo;
import com.yundingshuyuan.recruit.service.handler.CheckInHandler;
import com.yundingshuyuan.recruit.service.handler.CheckInHandlerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 二维码签到相关 Service
 *
 * @author wys
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeCheckInServiceImpl implements QrCodeCheckInService {

    private final QrCodeCheckInMapper qrCheckMapper;

    private final CheckInHandlerManager ciHandlerManager;

    /*private final QrCodeUtils qrCodeUtils;*/

    @Override
    public String createQrCode(String openId, String eventName, int expireTime) {
        // 创建 事件所需信息
        CheckInHandler checkinHandler = ciHandlerManager.getCheckInHandler(eventName);
        Object data = checkinHandler.handleByOpenId(openId, qrCheckMapper);
        // data 校验
        if (data == null) {
            throw new RuntimeException("无结果，错误的openId");
        }
        // 创建时间
        long createTimestamp = System.currentTimeMillis();
        long expireTimestamp = createTimestamp + expireTime * 1000L;
        // 加密
        try {
            String content = checkinHandler.encipher(data, createTimestamp, expireTimestamp);
            /*return qrCodeUtils.getQrCodeBase64(content);*/
            return content;
        } catch (Exception e) {
            throw new RuntimeException("解密失败");
        }
    }

    @Override
    public void parseQrCodeInfo(String scanInfo) {
        CheckInEventVo wrapper;
        // 根据事件名调用
        CheckInHandler<?> checkinHandler;
        // 解密被加密事件信息
        CheckInEvent<?> event;
        try {
            wrapper = JSON.parseObject(scanInfo, CheckInEventVo.class);
            // 根据事件名调用
            checkinHandler = ciHandlerManager.getCheckInHandler(wrapper.getEventName());
            // 解密被加密事件信息
            event = checkinHandler.decipher(wrapper.getEncryptedData());
        } catch (NullPointerException e) {
            throw new RuntimeException("二维码解析失败");
        }
       /* // 判断二维码是否过期
        long expireTimestamp = event.getExpireTimestamp();
        long serviceTime = System.currentTimeMillis();
        if (serviceTime > expireTimestamp) {
            throw new RuntimeException(StrUtil.format("\n錯誤[error]:\n二维码失效:at {} \n 当前服务器时间:currentTime {}",
                    DateUtil.format(new Date(expireTimestamp), CommonConstant.DATE_TIME_FORMAT_YMDHMSS),
                    DateUtil.format(new Date(serviceTime), CommonConstant.DATE_TIME_FORMAT_YMDHMSS)));
        }*/
        // 根据事件信息操作
        checkinHandler.doCheckIn(event, qrCheckMapper);
        // 日志
        log.info("签到事件成功:{}", LocalDateTime.now());
    }


}
