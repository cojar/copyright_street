package com.sbp.copyrightStreet.boundedContext.email;

import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "soone4704@gmail.com";
    private static final String TO_ADDRESS = "vx4704@naver.com";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void mailSend(MailDto mailDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            messageHelper.setFrom(FROM_ADDRESS);
            mailDto.setAddress("soone4704@gmail.com");

            messageHelper.setSubject(mailDto.getTitle());

            String htmlContent = "<p>" + mailDto.getMessage() + "<p> <img src='cid:sample-img'>";
            messageHelper.setText(htmlContent, true);

           // messageHelper.setTo(TO_ADDRESS);
            messageHelper.setTo("vx4704@naver.com");
           // mailDto.setRecipientEmail("vx4704@naver.com");
            mailDto.setRecipientEmail("vx4704@naver.com");


            String imageFilePath = "/Users/munchangbin/Documents/static/sample1.jpg";
//머지는 다됫어? 어제 내가 보채서 푸쉬받긴햇는데
            // 파일 추가
            FileDataSource fds = new FileDataSource(imageFilePath);
            messageHelper.addAttachment(MimeUtility.encodeText("sample1.jpg", "UTF-8", "B"), fds);
           // setAttach("sample1.jpg", mailDto.getAttachedFiles(), messageHelper);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("eoor");
            log.info(e.toString());
        }

    }

    public void setAttach(String displayFileName, List<MultipartFile> attachedFiles, MimeMessageHelper messageHelper) {
        try {
            if (attachedFiles != null && !attachedFiles.isEmpty()) {
                for (MultipartFile file : attachedFiles) {
                    if (file != null) { // null인 MultipartFile을 건너뜁니다.
                        byte[] fileBytes = file.getBytes();
                        ByteArrayResource resource = new ByteArrayResource(fileBytes);

                        String originalFileName = file.getOriginalFilename();
                        String actualFileName = (originalFileName != null && !originalFileName.isEmpty()) ? originalFileName : "unknown-file";

                        messageHelper.addAttachment(actualFileName, resource);
                    }
                }
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
    public void setInline(String contentId, byte[] imageBytes, MimeMessageHelper messageHelper) {
        try {
            //ByteArrayResource resource = new ByteArrayResource(imageBytes);
            DataSource imageDataSource = new ByteArrayDataSource(imageBytes, "image/jpg");
            //messageHelper.addInline(contentId, new InputStreamResource(new ByteArrayInputStream(imageBytes)),  "image/png");

            messageHelper.addInline(contentId, new InputStreamResource(new ByteArrayInputStream(imageBytes)),  "image/png");
            // messageHelper.addInline(contentId, resource);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}