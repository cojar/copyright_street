package com.sbp.copyrightStreet.boundedContext.payment;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // JSON 객체에 저장된 결제정보를 DB에 저장
//    @Transactional
//    public Payment savePayment(Integer amount, String orderId, String paymentKey) {
//
//        // JSON 객체에서 결제 정보를 추출해서 Payment 객체를 생성
//        Payment payment = Payment
//                .builder()
//                .amount(Long.valueOf(amount))
//                .orderNum(orderId)
//                .paymentDate(LocalDateTime.parse(paymentKey))
//                .build();
//
//        //생성된 payment 객체를 데이터베이스에 저장하고 저장된 객체를 반환
//        return paymentRepository.save(payment);
//    }
    @Transactional
    public Payment savePayment(JSONObject jsonObject) {

        // JSON 객체에서 결제 정보를 추출해서 Payment 객체를 생성
        Payment payment = Payment.builder()
                .method((String) jsonObject.get("method"))
                .amount(Long.valueOf((Integer) jsonObject.get("amount")))
                .orderNum((String) jsonObject.get("orderId"))
                .orderName((String) jsonObject.get("orderName"))
                .build();

        //생성된 payment 객체를 데이터베이스에 저장하고 저장된 객체를 반환
        return paymentRepository.save(payment);
    }

}