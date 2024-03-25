package com.yundingshuyuan.framework.starter.bases.init;

import com.yundingshuyuan.framework.starter.bases.init.events.ApplicationInitializingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应用初始化后置处理器，防止Spring事件被多次执行
 */
@RequiredArgsConstructor
@Slf4j
public class ApplicationContentPostProcessor implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationContext applicationContext;

    /**
     * 执行计数器，记录Spring事件 {@link ApplicationReadyEvent} 执行的次数
     */
    private final AtomicInteger executionCount = new AtomicInteger(0);

    @Order(1)
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (executionCount.getAndIncrement() > 0) {
            log.info("ApplicationReadyEvent has been executed {} times", executionCount.get());
            return;
        }
        log.info("Publishing ApplicationInitializingEvent");
        // 发送自定义事件 ApplicationInitializingEvent
        applicationContext.publishEvent(new ApplicationInitializingEvent(this));
    }
}