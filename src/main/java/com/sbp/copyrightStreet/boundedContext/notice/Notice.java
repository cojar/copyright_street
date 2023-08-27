package com.sbp.copyrightStreet.boundedContext.notice;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@NoArgsConstructor // @Builder 붙이면 이거 필수
@AllArgsConstructor // @Builder 붙이면 이거 필수
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate 작동하게 허용
@ToString // 디버그를 위한
@Entity// 아래 클래스는 member 테이블과 대응되고, 아래 클래스의 객체는 테이블의 row와 대응된다.
@Setter
@Getter // 아래 필드에 대해서 전부다 게터를 만든다. private Long id; => public Long getId() { ... }
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;// 제목

    @Column(columnDefinition = "TEXT")
    private String content;// 내용

    @ManyToOne
    private Member admin;// 작성자

    @CreatedDate
    private LocalDateTime createDate;//작성시간

    private int hitCount;//조회수

    @Transient//JPA리포지터리를 사용할때 적용하는 어노테이션으로 데이터베이스에 저장되지 않고 일시적으로 사용하는 어노테이션
    // 데이터베이스에 저장하려면 이 어노테이션을 사용하지 않아야 함
    private Notice previous;
    @Transient
    private Notice next;

}