package com.sbp.copyrightStreet.boundedContext.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "soone4704@gmail.com";
    private static final String TO_ADDRESS = "vx4704@naver.com";

    public void mailSend(MailDto mailDto) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            mailHandler.setFrom(FROM_ADDRESS);
            mailDto.setAddress("soone4704@gmail.com");

            mailHandler.setSubject(mailDto.getTitle());

            String htmlContent = "<p>" + mailDto.getMessage() + "<p> <img src='cid:sample-img'>";
            mailHandler.setText(htmlContent, true);

            mailHandler.setTo(TO_ADDRESS);

            mailHandler.setAttach("newTest.txt", mailDto.getAttachedFiles());

            String imageFilePath = "/Users/munchangbin/Documents/static/sample1.jpg";
            byte[] imageBytes = Files.readAllBytes(Paths.get(imageFilePath)); // 이미지 파일 읽어옴
            mailHandler.setInline("sample-img", imageBytes);

            mailHandler.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}