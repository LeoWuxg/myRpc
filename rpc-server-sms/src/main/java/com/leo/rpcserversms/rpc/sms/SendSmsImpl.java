package com.leo.rpcserversms.rpc.sms;

import com.leo.rpcserversms.remote.serviceInterface.SendSms;
import com.leo.rpcserversms.remote.vo.UserInfo;

/**
 * @ClassName: SendSmsImpl
 * @Description: 发送短信的实现类
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
public class SendSmsImpl implements SendSms {

    @Override
    public boolean sendPhoneMsg(UserInfo user) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("已发送短信息给：" + user.toString());
        return true;
    }
}
