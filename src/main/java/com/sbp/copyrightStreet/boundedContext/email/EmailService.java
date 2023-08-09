package com.sbp.copyrightStreet.boundedContext.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "soone4704@gmail.com";

    public void mailSend(MailDto mailDto) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            // 받는 사람
            mailHandler.setTo(mailDto.getAddress());
            // 보내는 사람
            mailHandler.setFrom(EmailService.FROM_ADDRESS);
            // 제목
            mailHandler.setSubject(mailDto.getTitle());
            // HTML Layout
            String htmlContent = "<p>" + mailDto.getMessage() +"<p> <img src='cid:sample-img'>";
            mailHandler.setText(htmlContent, true);

            // 절대 파일 경로 설정
            String absoluteFilePath = "/Users/munchangbin/originTest.txt";
            mailHandler.setAttach("newTest.txt", absoluteFilePath);

            // 이미지 삽입
            String imageFilePath = "/Users/munchangbin/Documents/static/sample1.jpg";
            mailHandler.setInline("sample-img", imageFilePath);

            mailHandler.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
