package com.leo.rpcclient.client.config;

import com.leo.rpcclient.client.rpc.RpcClientFrame;
import com.leo.rpcclient.remote.serviceInterface.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: BeanConfig
 * @Description: TODO
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
@Configuration
public class BeanConfig {
    @Autowired
    private RpcClientFrame rpcClientFrame;

    @Bean
    public SendSms getSmsService() {
        return rpcClientFrame.getRemoteProxyObject(SendSms.class);
    }
}
