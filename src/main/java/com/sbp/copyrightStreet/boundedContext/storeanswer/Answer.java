package com.sbp.copyrightStreet.boundedContext.storeanswer;



import com.sbp.copyrightStreet.base.BaseEntity;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.storequestion.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Answer extends BaseEntity {
    private String comment;
    @OneToOne
    private Member member;
    @OneToOne
    private Question question;
}
