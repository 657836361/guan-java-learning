package com.guan.learning.chain.service;

import org.springframework.core.OrderComparator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessChainFactory {

    /**
     * 控制排序，只有首次访问时排序
     */
    private static AtomicBoolean initOrder = new AtomicBoolean(false);

    /**
     * 存储登录处理服务
     */
    private static final List<AbstractProcessHandler> PROCESS_CHAIN = new CopyOnWriteArrayList<>();

    /**
     * @param service
     */
    public static void register(AbstractProcessHandler service) {
        PROCESS_CHAIN.add(service);
    }

    /**
     * 获取登录服务处理链
     *
     * @return
     */
    public static List<AbstractProcessHandler> getHandlerChain() {
        if (initOrder.compareAndSet(false, true)) {
            OrderComparator.sort(PROCESS_CHAIN);
        }
        return PROCESS_CHAIN;
    }
}
