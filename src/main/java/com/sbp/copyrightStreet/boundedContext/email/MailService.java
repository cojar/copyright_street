package com.sbp.copyrightStreet.boundedContext.email;



public interface MailService {


    /** 메일 전송
     *  @param subject 제목
     *  @param text 내용
     *  @param from 보내는 메일 주소
     *  @param to 받는 메일 주소  **/
    public boolean send(String subject, String text, String from, String to);

}