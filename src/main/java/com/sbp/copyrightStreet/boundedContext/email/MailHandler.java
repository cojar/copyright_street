package com.sbp.copyrightStreet.boundedContext.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MailHandler {
    private JavaMailSender sender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    // 생성자
    public MailHandler(JavaMailSender jSender) throws
            MessagingException {
        this.sender = jSender;
        message = jSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    // 보내는 사람 이메일
    public void setFrom(String fromAddress) throws MessagingException {
        messageHelper.setFrom(fromAddress);
    }

    // 받는 사람 이메일
    public void setTo(String toAddress) throws MessagingException {
        messageHelper.setTo(toAddress);
    }


    // 제목
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }


    // 메일 내용
    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    // 첨부 파일
    public void setAttach(String displayFileName, List<MultipartFile> attachedFiles) throws MessagingException, IOException {
        for (MultipartFile file : attachedFiles) {
            byte[] fileBytes = file.getBytes();
            ByteArrayResource resource = new ByteArrayResource(fileBytes);
            messageHelper.addAttachment(displayFileName, resource);
        }
    }

    // 이미지 삽입
    public void setInline(String contentId, byte[] imageBytes) throws MessagingException {
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        messageHelper.addInline(contentId, resource);
    }

    // 발송
    public void send() {
        try {
            sender.send(message);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}