package com.sbp.copyrightStreet.boundedContext.storeanswer;


import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.storequestion.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String comment;

    @OneToOne
    private Member member;

    @OneToOne
    private Question question;

    private LocalDateTime regTime;

    private LocalDateTime modifyTime;
}
