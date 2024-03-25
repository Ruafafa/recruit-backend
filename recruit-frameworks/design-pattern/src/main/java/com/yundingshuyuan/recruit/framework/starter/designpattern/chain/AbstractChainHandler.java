package com.yundingshuyuan.recruit.framework.starter.designpattern.chain;

import org.springframework.core.Ordered;
import java.util.function.Consumer;

public interface AbstractChainHandler<T> extends Ordered {

    /**
     * 执行责任链逻辑
     *
     * @param requestParam 责任链执行入参
     */
    void handler(T requestParam);

    /**
     * 默认执行责任链逻辑
     *
     * @param requestParam 责任链执行入参
     * @param consumer 提供默认处理逻辑的消费者
     */
    default void defaultHandler(T requestParam, Consumer<T> consumer) {
        consumer.accept(requestParam);
    }

    /**
     * @return 责任链组件标识
     */
    String mark();
}