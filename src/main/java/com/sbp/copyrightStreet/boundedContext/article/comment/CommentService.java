package com.sbp.copyrightStreet.boundedContext.article.comment;

import com.sbp.copyrightStreet.DataNotFoundException;
import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Board board, String content, Member author){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setBoard(board);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
    }

    public Comment getComment(Integer id) {// Integer 로 타입이 들어오면 null 값도 허용해줄 수 있음
        Optional<Comment> answer = this.commentRepository.findById(id);

        if(answer.isPresent()){
            return answer.get();
        }else{
            throw new DataNotFoundException("comment not found");
        }


    }
    public Comment getAnswer(Integer id) {
        Optional<Comment> answer = this.commentRepository.findById(id);
        return answer.get();

    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }
    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

}
