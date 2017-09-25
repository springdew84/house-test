package com.cassey.house.mail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.cassey.house.mail.Email;

public class EmailTest {

	@Test
	public void sendEmail() throws Exception {
		String userName = "11111111@qq.com"; // 发件人邮箱
		String password = "********"; // 发件人密码
		String smtpHost = "smtp.qq.com"; // 邮件服务器

		String to = "22222222@qq.com"; // 收件人，多个收件人以半角逗号分隔
		String cc = "33333333@qq.com"; // 抄送，多个抄送以半角逗号分隔
		String subject = "这是邮件的主题"; // 主题
		String body = "这是邮件的正文"; // 正文，可以用html格式的哟
		List<String> attachments = Arrays.asList("D:\\tmp\\1.png", "D:\\tmp\\2.png"); // 附件的路径，多个附件也不怕

		Email email = Email.entity(smtpHost, userName, password, to, cc, subject, body, attachments);

		email.send(); // 发送！
	}

}
