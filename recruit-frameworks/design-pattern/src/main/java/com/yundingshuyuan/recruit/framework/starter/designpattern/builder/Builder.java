package com.yundingshuyuan.recruit.framework.starter.designpattern.builder;

import java.io.Serializable;
import java.util.function.Supplier;

public interface Builder<T> extends Serializable {

    /**
     * 构建方法
     *
     * @return 构建后的对象
     */
    T build();

    /**
     * 默认构建方法
     *
     * @param supplier 提供默认构建逻辑的供应器
     * @return 构建后的对象
     */
    default T defaultBuild(Supplier<T> supplier) {
        return supplier.get();
    }
}