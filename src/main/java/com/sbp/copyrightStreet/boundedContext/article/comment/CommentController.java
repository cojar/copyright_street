package com.sbp.copyrightStreet.boundedContext.article.comment;

import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.article.borad.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @RequestParam String content){
        Board board = this.boardService.getBoard(id);
        this.commentService.create(board, content);
        return String.format("redirect:/board/detail/%s",id);
    }

    @GetMapping("/modify/{id}")
    public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);

        commentForm.setContent(comment.getContent());
        return "comment_form";
    }
//    @PostMapping("/modify/{id}")
//    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
//                               @PathVariable("id") Integer id, Principal principal) {
//        if (bindingResult.hasErrors()) {
//            return "comment_form";
//        }
//        Comment comment = this.commentService.getAnswer(id);
//        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//        this.answerService.modify(answer, answerForm.getContent());
//        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
//    }
}