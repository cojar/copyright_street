package com.sbp.copyrightStreet.boundedContext.notice;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@NoArgsConstructor // @Builder 붙이면 이거 필수
@AllArgsConstructor // @Builder 붙이면 이거 필수
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate 작동하게 허용
@ToString // 디버그를 위한
@Entity // 아래 클래스는 member 테이블과 대응되고, 아래 클래스의 객체는 테이블의 row와 대응된다.
@Getter // 아래 필드에 대해서 전부다 게터를 만든다. private Long id; => public Long getId() { ... }
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title; // 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    @Column
    private String author;
}