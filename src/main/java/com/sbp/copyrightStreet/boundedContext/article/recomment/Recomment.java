package com.sbp.copyrightStreet.boundedContext.article.recomment;

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
public class Recomment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Recomment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Recomment> children;

    @ManyToMany
    private Set<Member> voter;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}