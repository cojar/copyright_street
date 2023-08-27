package com.sbp.copyrightStreet.boundedContext.payment;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Value("${payment.secretKey}")
    private String paymentSecretKey;

    private final PaymentService paymentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/success")
    public String paymentResult(
            Model model,
            @RequestParam String orderId,
            @RequestParam Integer amount,
            @RequestParam String paymentKey) throws Exception {

        Base64.Encoder encoder = Base64.getEncoder();

        String authString = paymentKey + ":" + paymentSecretKey;
        byte[] encodedBytes = encoder.encodeToString(authString.getBytes(StandardCharsets.UTF_8)).getBytes();
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

//      String authorizations = "Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSICsgOg==";
//        Authorization: Basic dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSICsgOg==



        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);


        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;
        model.addAttribute("isSuccess", isSuccess);

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        model.addAttribute("responseStr", jsonObject.toJSONString());
        System.out.println(jsonObject.toJSONString());


        model.addAttribute("method", (String) jsonObject.get("method"));
        model.addAttribute("orderName", (String) jsonObject.get("orderName"));
        model.addAttribute("orderId", (String) jsonObject.get("orderId"));
        model.addAttribute("amount", (Integer) jsonObject.get("amount"));


        if (((String) jsonObject.get("method")) != null) {
            if (((String) jsonObject.get("method")).equals("카드")) {
                model.addAttribute("cardNumber", (String) ((JSONObject) jsonObject.get("card")).get("number"));
            } else if (((String) jsonObject.get("method")).equals("가상계좌")) {
                model.addAttribute("accountNumber", (String) ((JSONObject) jsonObject.get("virtualAccount")).get("accountNumber"));
            } else if (((String) jsonObject.get("method")).equals("계좌이체")) {
                model.addAttribute("bank", (String) ((JSONObject) jsonObject.get("transfer")).get("bank"));
            } else if (((String) jsonObject.get("method")).equals("휴대폰")) {
                model.addAttribute("customerMobilePhone", (String) ((JSONObject) jsonObject.get("mobilePhone")).get("customerMobilePhone"));
            }
        } else {
            model.addAttribute("code", (String) jsonObject.get("code"));
            model.addAttribute("message", (String) jsonObject.get("message"));
        }


        // 결제 정보를 데이터베이스에 저장
        Payment payment = this.paymentService.savePayment(jsonObject);
        //저장된 결제정보를 모델에 추가

        return "membership/success";
    }

    //결제 정보 상세
    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam String method,
                         @RequestParam Long amount,
                         @RequestParam String orderId,
                         @RequestParam String orderName) {

        // 결제 정보를 JSON 객체에 저장
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", method);
        jsonObject.put("amount", amount);
        jsonObject.put("orderId", orderId);
        jsonObject.put("orderName", orderName);

        // 결제 정보를 데이터베이스에 저장
        Payment payment = this.paymentService.savePayment(jsonObject);
        //저장된 결제정보를 모델에 추가
        model.addAttribute("paymentInfo", payment);

        return "redirect:/mypage/detail";
    }

    @GetMapping("/fail")

    public String paymentResult(Model model,
                                @RequestParam(value = "message") String message,
                                @RequestParam(value = "code") Integer code) throws Exception {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return "membership/fail";
    }

}