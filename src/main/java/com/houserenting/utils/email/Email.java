package com.houserenting.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Email {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);//địa chỉ nhận email
        mailMessage.setSubject(subject);//tên email
        mailMessage.setText(content);//nội dung email
        javaMailSender.send(mailMessage);//phương thức gửi mail
    }
}
