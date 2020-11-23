package com.leo.rpcclient;

import com.leo.rpcclient.client.service.ClientBusinessService;
import com.leo.rpcclient.remote.serviceInterface.SendSms;
import com.leo.rpcclient.remote.vo.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpcClientApplicationTests {

	@Autowired
	private ClientBusinessService clientBusinessService;

	//发送短信service Interface
	@Autowired
	private SendSms sendSms;

	@Test
	void contextLoads() {
	}

	@Test
	void rpcSendSmsTest() {
		long start = System.currentTimeMillis();
		//客户端业务逻辑
		clientBusinessService.businessHandler();

		//发送短信
		UserInfo userInfo = new UserInfo("Leo", "xxxxxxxxxx");
		System.out.println("Send phoneMsg: " + sendSms.sendPhoneMsg(userInfo));

		System.out.println("共耗时: " + (System.currentTimeMillis() - start) + " ms.");
	}
}
