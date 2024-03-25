package com.yundingshuyuan.recruit.framework.starter.designpattern.strategy;

import java.util.function.Consumer;

public interface AbstractExecuteStrategy<REQUEST, RESPONSE> {

    /**
     * 执行策略标识
     */
    default String mark() {
        return null;
    }

    /**
     * 执行策略范匹配标识
     */
    default String patternMatchMark() {
        return null;
    }

    /**
     * 执行策略
     *
     * @param requestParam 执行策略入参
     */
    default void execute(REQUEST requestParam) {

    }

    /**
     * 默认执行策略
     *
     * @param requestParam 执行策略入参
     * @param consumer 提供默认处理逻辑的消费者
     */
    default void defaultExecute(REQUEST requestParam, Consumer<REQUEST> consumer) {
        consumer.accept(requestParam);
    }

    /**
     * 执行策略，带返回值
     *
     * @param requestParam 执行策略入参
     * @return 执行策略后返回值
     */
    default RESPONSE executeResp(REQUEST requestParam) {
        return null;
    }
}