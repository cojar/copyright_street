package com.sbp.copyrightStreet.boundedContext.article.comment;

import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.article.recomment.Recomment;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @ManyToOne
    private Member author; // 댓글쓴이

    @ManyToOne
    private Board board; // 해당 댓글이 있는 게시글

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Recomment> reCommentList; // 대댓글

    @ManyToMany
    private Set<Member> voter; //추천 좋아요

    @CreatedDate
    private LocalDateTime createDate; // 댓글 생성일

    private LocalDateTime modifyDate;  // 수정
}
