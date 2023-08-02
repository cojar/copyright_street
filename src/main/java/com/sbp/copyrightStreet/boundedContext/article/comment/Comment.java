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
    private String content;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Recomment> reCommentList;

    @ManyToMany
    private Set<Member> voter;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
