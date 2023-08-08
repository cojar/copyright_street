package com.sbp.copyrightStreet.boundedContext.article.recomment;

import com.sbp.copyrightStreet.boundedContext.article.comment.Comment;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommentService {
    private final RecommentRepository recommentRepository;

    public List<Recomment> getList() {
        return this.recommentRepository.findAll();
    }

    public Recomment getRecomment(Long id) {
        Optional<Recomment> recomment = this.recommentRepository.findById(id);
        return recomment.get();
    }

    public Recomment create(String content, Member author, Comment comment, Recomment parent) {

        Recomment reComment = new Recomment();

        reComment.setContent(content);
        reComment.setAuthor(author);
        reComment.setComment(comment);
        if (parent != null) reComment.setParent(parent);
        reComment.setCreateDate(LocalDateTime.now());

        this.recommentRepository.save(reComment);

        return reComment;
    }
}

