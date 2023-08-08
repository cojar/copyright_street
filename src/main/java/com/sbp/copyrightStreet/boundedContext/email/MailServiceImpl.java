package com.sbp.copyrightStreet.boundedContext.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {

    // org.springframework.mail.javamail.JavaMailSender
    @Autowired
    JavaMailSender javaMailSender;

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        System.out.println("들어오냐");
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean send(String subject, String text, String from, String to) {
        System.out.println("send에 들어옴");
        // javax.mail.internet.MimeMessage
        MimeMessage message = javaMailSender.createMimeMessage();
        System.out.println("message의 값 : " + message);

        try {
            // org.springframework.mail.javamail.MimeMessageHelper
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom(from);
            helper.setTo(to);

            javaMailSender.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

}