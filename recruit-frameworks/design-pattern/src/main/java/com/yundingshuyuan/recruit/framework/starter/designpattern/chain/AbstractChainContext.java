package com.yundingshuyuan.recruit.framework.starter.designpattern.chain;

import com.yundingshuyuan.recruit.framework.starter.common.utils.SpringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象责任链上下文
 * 这个类管理了一组责任链处理器，并提供了一个方法来处理请求。
 */
public final class AbstractChainContext<T> implements CommandLineRunner {

    // 存储责任链处理器的容器
    private final Map<String, List<AbstractChainHandler>> abstractChainHandlerContainer = new HashMap<>();

    /**
     * 责任链组件执行
     *
     * 这个方法接受一个责任链组件标识和一个请求参数，然后找到对应的责任链处理器来处理请求。
     *
     * @param mark         责任链组件标识
     * @param requestParam 请求参数
     */
    public void handler(String mark, T requestParam) {
        // 从容器中获取责任链处理器
        List<AbstractChainHandler> abstractChainHandlers = abstractChainHandlerContainer.get(mark);
        // 如果没有找到对应的责任链处理器，抛出异常
        if (CollectionUtils.isEmpty(abstractChainHandlers)) {
            throw new RuntimeException(String.format("[%s] Chain of Responsibility ID is undefined.", mark));
        }
        // 遍历责任链处理器，处理请求
        abstractChainHandlers.forEach(each -> each.handler(requestParam));
    }

    /**
     * 初始化责任链
     * 这个方法在应用启动时被调用，它会从Spring容器中获取所有的责任链处理器，然后根据它们的顺序添加到容器中。
     */
    @Override
    public void run(String... args) throws Exception {
        // 从Spring容器中获取所有的责任链处理器
        Map<String, AbstractChainHandler> chainFilterMap = SpringUtils
                .getBeansOfType(AbstractChainHandler.class);
        // 遍历责任链处理器
        chainFilterMap.forEach((beanName, bean) -> {
            // 从容器中获取责任链处理器
            List<AbstractChainHandler> abstractChainHandlers = abstractChainHandlerContainer.get(bean.mark());
            // 如果没有找到对应的责任链处理器，创建一个新的列表
            if (CollectionUtils.isEmpty(abstractChainHandlers)) {
                abstractChainHandlers = new ArrayList();
            }
            // 将责任链处理器添加到列表中
            abstractChainHandlers.add(bean);
            // 根据责任链处理器的顺序对列表进行排序
            List<AbstractChainHandler> actualAbstractChainHandlers = abstractChainHandlers.stream()
                    .sorted(Comparator.comparing(Ordered::getOrder))
                    .collect(Collectors.toList());
            // 将排序后的列表添加到容器中
            abstractChainHandlerContainer.put(bean.mark(), actualAbstractChainHandlers);
        });
    }
}