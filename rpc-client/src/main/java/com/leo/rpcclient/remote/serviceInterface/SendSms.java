package com.leo.rpcclient.remote.serviceInterface;

import com.leo.rpcclient.remote.vo.UserInfo;

/**
 * @ClassName: SendSms
 * @Description: 短信发送接口
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
public interface SendSms {
    boolean sendPhoneMsg(UserInfo user);
}
