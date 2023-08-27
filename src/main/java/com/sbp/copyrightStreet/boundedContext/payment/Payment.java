package com.sbp.copyrightStreet.boundedContext.payment;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor // @Builder 붙이면 이거 필수
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   //순번

    private String method; //주문방법

    @Column(nullable = false)
    private Long amount;   //가격 basic:15000, primium:29000, pro:49000

    @Column(nullable = false)
    private String orderNum; //주문번호 <-orderId

    @Column(nullable = false)
    private String orderName;  //라이센스이름

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate;  // 결제날짜

    @ManyToOne
    private Member member; // member 객체와 다:1 관계
}