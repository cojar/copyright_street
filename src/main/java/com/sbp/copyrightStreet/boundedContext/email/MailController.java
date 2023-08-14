package com.sbp.copyrightStreet.boundedContext.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.sbp.copyrightStreet.boundedContext.email.MailService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@Controller
public class MailController {
    private final EmailService emailService;
    @Autowired
    private MailService mailService;


    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }


    @RequestMapping(value = "/checkEmailAjax.do")
    @ResponseBody
    public Map<String, String> sendMail(@RequestBody Map<String, String> map, HttpSession session) {

        System.out.println("/checkEmail.do에 들어옴");
        System.out.println("입력받은 email의 값 : " + map.get("email"));

        int random = new Random().nextInt(100000) + 10000; // 10000 ~ 99999
        System.out.println("random의 값 : " + random);

        String joinCode = String.valueOf(random);
        System.out.println("joinCode의 값 : " + joinCode);

        session.setAttribute("joinCode", joinCode);

        String subject = "저작거리 홈페이지 회원가입 인증 코드 입니다.";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("안녕하세요. '저작거리'입니다.\r귀하의 인증 코드는  <" + joinCode + "> 입니다.");
        System.out.println(stringBuilder.toString());

        boolean finishSend = this.mailService.send(subject, stringBuilder.toString(), "TongAdmin", map.get("email"));
        System.out.println("성공이냐 실패냐 : " + finishSend);

        map.put("joinCode", joinCode);

        System.out.println(map);

        return map;
    }

    @GetMapping("/copy/author")
    public String dispMail() {
        return "mail";
    }

    @PostMapping("/copy/author")
    public String execMail(MailDto mailDto, @RequestParam("attachedFiles") List<MultipartFile> attachedFiles) {
        mailDto.setAttachedFiles(attachedFiles);
        emailService.mailSend(mailDto);
        return "redirect:/copy/author";
    }
}