package com.sbp.copyrightStreet.boundedContext.article.borad;


import com.sbp.copyrightStreet.boundedContext.article.comment.Comment;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title; // 제목

    private String content; // 내용

    @ManyToOne
    private Member author; //글쓴이

    private String category; //분류 카테고리
    //
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList; //댓글

        @ManyToMany
    Set<Member> voter; //추천
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;


    @CreatedDate
    private LocalDateTime createDate; // 생성일

    private LocalDateTime modifyDate; // 수정일
}