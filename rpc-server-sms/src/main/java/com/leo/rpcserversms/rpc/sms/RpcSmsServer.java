package com.leo.rpcserversms.rpc.sms;

import com.leo.rpcserversms.remote.serviceInterface.SendSms;
import com.leo.rpcserversms.rpc.base.RpcServerFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @ClassName: RpcSmsServer
 * @Description: rpc服务端，提供服务
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
@Service
public class RpcSmsServer {
    //rpc服务框架
    @Autowired
    private RpcServerFrame rpcServerFrame;

    //rpc服务IP地址
    private static final String serverHost = "127.0.0.1";

    @PostConstruct
    public void server() throws IOException {
        //服务端口
        int port = 8888;
        //启动服务
        rpcServerFrame.startService(SendSms.class.getName(), SendSmsImpl.class, serverHost, port);
    }
}
