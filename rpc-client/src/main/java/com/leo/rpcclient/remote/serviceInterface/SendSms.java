package com.leo.rpcserversms.remote.serviceInterface;

import com.leo.rpcserversms.remote.vo.UserInfo;

/**
 * @ClassName: SendSms
 * @Description: 短信发送接口
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
public interface SendSms {
    boolean sendMail(UserInfo user);
}
