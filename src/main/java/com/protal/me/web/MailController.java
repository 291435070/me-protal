package com.protal.me.web;

import com.protal.me.util.ResultBody;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDateTime;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("send")
    public Object send() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("3002128637@qq.com");
        message.setSubject("勿回复:系统自动发送");
        message.setText("今天是：" + LocalDateTime.now() + "，天气晴朗，适合户外活动。");
        javaMailSender.send(message);

        return message;
    }

    @RequestMapping("send/html")
    public Object sendHtml() throws Exception {
        String pathname = this.getClass().getResource("/templates/alert.html").getPath();
        String text = FileUtils.readFileToString(new File(pathname), "UTF-8");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo("3002128637@qq.com");
        helper.setSubject("勿回复:系统自动发送");
        helper.setText(text, true);
        javaMailSender.send(message);

        return new ResultBody(ResultBody.StatusEnum.SUCCESS, text);
    }
}