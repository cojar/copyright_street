package com.sbp.copyrightStreet.boundedContext.payment;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment savePayment(JSONObject jsonObject) {
        Payment payment = Payment.builder()
                .paymentKey((String) jsonObject.get("paymentKey"))
                .method((String) jsonObject.get("method"))
                .amount((Long) jsonObject.get("amount"))
                .orderNum((String) jsonObject.get("orderId"))
                .orderName((String) jsonObject.get("orderName"))
                .build();
        return paymentRepository.save(payment);
    }

}

