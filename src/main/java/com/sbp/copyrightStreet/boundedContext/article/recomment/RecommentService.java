package com.sbp.copyrightStreet.boundedContext.article.recomment;

import com.sbp.copyrightStreet.boundedContext.article.comment.Comment;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RecommentService {
    private final RecommentRepository recommentRepository;

    public List<Recomment> getList() {
        return this.recommentRepository.findAll();
    }

    public Recomment getRecomment(Long id) {
        Optional<Recomment> recomment = this.recommentRepository.findById(id);
        return recomment.get();
    }

    public Recomment create(Comment comment, String  recomment) {
        Recomment r = new Recomment();
        r.setComment(comment);
        r.setCreateDate(LocalDateTime.now());
        r.setRecomment(recomment);
        this.recommentRepository.save(r);
        return r;
    }
}

