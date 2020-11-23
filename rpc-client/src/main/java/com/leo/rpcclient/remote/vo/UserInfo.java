package com.leo.rpcclient.remote.vo;

import java.io.Serializable;

/**
 * @ClassName: UserInfo
 * @Description: 用户实体类，需事先序列化
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
public class UserInfo implements Serializable {
    private final String name;
    private final String phone;

    public UserInfo(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("UserInfo [name:%s, phone:%s]", name, phone);
    }
}
