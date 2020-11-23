package com.leo.rpcserversms.rpc.base;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: RegisterService
 * @Description: 本地服务注册
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
@Service
public class RegisterService {
    //本地可提供服务的容器
    private static final Map<String, Class> serviceCache = new ConcurrentHashMap<>();

    /**
     * 将服务放进服务容器
     * @param serviceName
     * @param clazz
     */
    public void registerService(String serviceName, Class clazz) {
        serviceCache.put(serviceName, clazz);
    }

    /**
     * 拿到本地服务
     * @param serviceName
     * @return
     */
    public Class getService(String serviceName) {
        return serviceCache.get(serviceName);
    }
}
