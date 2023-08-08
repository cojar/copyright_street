package com.sbp.copyrightStreet.boundedContext.article.borad;

import com.sbp.copyrightStreet.boundedContext.article.category.Category;
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
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToMany
    private Set<Member> voter;

    private Integer hit;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}