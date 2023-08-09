//package com.sbp.copyrightStreet.boundedContext.email;
//
//import jakarta.activation.DataHandler;
//import jakarta.activation.DataSource;
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.internet.MimeMultipart;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.StringTokenizer;
//
//
//@Slf4j
//@WebServlet("/WebSendMail")
//public class WebSendMail extends HttpServlet {
//
//
//    String to = "soone4704@gmail.com";
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (request.getContentType().startsWith("multipart/form-data")) {
//            try {
//                HashMap data = getMailData(request, response);
//                sendMail(data);
//
//                ServletContext sc = getServletContext();
//                RequestDispatcher rd = sc.getRequestDispatcher("/thankyou.html");
//                rd.forward(request, response);
//            } catch (MessagingException ex) {
//                throw new ServletException(ex);
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//
//    private HashMap getMailData(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException, MessagingException {
//        String boundary = request.getHeader("Content-Type");
//        int pos = boundary.indexOf('=');
//        boundary = boundary.substring(pos + 1);
//        boundary = "--" + boundary;
//        ServletInputStream in = request.getInputStream();
//        byte[] bytes = new byte[512];
//        int state = 0;
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        String name = null, value = null, filename = null, contentType = null;
//        HashMap mailData = new HashMap();
//
//        int i = in.readLine(bytes, 0, 512);
//        while (-1 != i) {
//            String st = new String(bytes, 0, i);
//            if (st.startsWith(boundary)) {
//                state = 0;
//                if (null != name) {
//                    if (value != null)
//                        // -2 to remove CR/LF
//                        mailData.put(name, value.substring(0, value.length() - 2));
//                    else if (buffer.size() > 2) {
//                        MimeBodyPart bodyPart = new MimeBodyPart();
//                        DataSource ds = new ByteArrayDataSource(buffer.toByteArray(), contentType, filename);
//                        bodyPart.setDataHandler(new DataHandler(ds));
//                        bodyPart.setDisposition("attachment; filename=\"" + filename + "\"");
//                        bodyPart.setFileName(filename);
//                        mailData.put(name, bodyPart);
//                    }
//                    name = null;
//                    value = null;
//                    filename = null;
//                    contentType = null;
//                    buffer = new ByteArrayOutputStream();
//                }
//            } else if (st.startsWith("Content-Disposition: form-data") && state == 0) {
//                StringTokenizer tokenizer = new StringTokenizer(st, ";=\"");
//                while (tokenizer.hasMoreTokens()) {
//                    String token = tokenizer.nextToken();
//                    if (token.startsWith(" name")) {
//                        name = tokenizer.nextToken();
//                        state = 2;
//                    } else if (token.startsWith(" filename")) {
//                        filename = tokenizer.nextToken();
//                        StringTokenizer ftokenizer = new StringTokenizer(filename, "\\/:");
//                        filename = ftokenizer.nextToken();
//                        while (ftokenizer.hasMoreTokens())
//                            filename = ftokenizer.nextToken();
//                        state = 1;
//                        break;
//                    }
//                }
//            } else if (st.startsWith("Content-Type") && state == 1) {
//                pos = st.indexOf(":");
//                // + 2 to remove the space
//                // - 2 to remove CR/LF
//                contentType = st.substring(pos + 2, st.length() - 2);
//            } else if (st.equals("\r\n") && state == 1)
//                state = 3;
//            else if (st.equals("\r\n") && state == 2)
//                state = 4;
//            else if (state == 4)
//                value = value == null ? st : value + st;
//            else if (state == 3)
//                buffer.write(bytes, 0, i);
//            i = in.readLine(bytes, 0, 512);
//        }
//        return mailData;
//    }
//
//    private void sendMail(HashMap mailData) throws MessagingException {
//        log.info("이메일 전송~~~");
//        System.setProperty("mail.smtp.starttls.enable", "true"); // gmail은 무조건 true 고정
//        System.setProperty("mail.smtp.auth", "true"); // gmail은 무조건 true 고정
//        System.setProperty("mail.smtp.host", "smtp.gmail.com"); // smtp 서버 주소
//        System.setProperty("mail.smtp.port", "587"); // gmail 포트
//        //구글 인증
//        Authenticator auth = new MyAuthentication();
//        Message msg = new MimeMessage(Session.getDefaultInstance(System.getProperties(), auth));
//        //받는사람
//        InternetAddress[] tos = InternetAddress.parse(to);
//        msg.setRecipients(Message.RecipientType.TO, tos);
//        //한글을 위한 인코딩
//        msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
//        //제목
//        msg.setSubject((String)mailData.get("subject"));
//        msg.setSentDate(new Date());
//
//        //첨부파일이 없으면 내용만 전송
//        if(null == mailData.get("attachment")){
//            msg.setText((String)mailData.get("body"));
//        } else {
//            //첨부파일이 있으면
//            BodyPart body = new MimeBodyPart();
//            BodyPart attachment = (BodyPart)mailData.get("attachment");
//            body.setText((String)mailData.get("body"));
//            MimeMultipart multipart = new MimeMultipart();
//            multipart.addBodyPart(body);
//            multipart.addBodyPart(attachment);
//            msg.setContent(multipart, "text/plain; charset=UTF-8");
//        }
//        try {
//            log.info("Sending email...");
//            Transport.send(msg);
//            log.info("Email sent successfully.");
//        } catch (MessagingException e) {
//            log.error("Error sending email: {}", e.getMessage());
//            throw e;
//        }
//        Transport.send(msg);
//    }
//
//    class MyAuthentication extends Authenticator {
//
//        private PasswordAuthentication pa;
//        private String id;
//        private String pw;
//
//        private MyAuthentication() {
//
//            id = "soone4704@gmail.com";
//            pw = "krjpynplmioubwox";
//            pa = new PasswordAuthentication(id, pw);
//        }
//
//
//        public PasswordAuthentication getPasswordAuthentication() {
//            return pa;
//        }
//    }
//
//}