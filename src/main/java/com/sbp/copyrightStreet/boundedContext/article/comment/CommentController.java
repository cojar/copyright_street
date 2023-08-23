package com.sbp.copyrightStreet.boundedContext.article.comment;

import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.article.borad.BoardService;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @RequestParam String content, Principal principal, @Valid CommentForm commentForm, BindingResult bindingResult){
        Board board = this.boardService.getBoard(id);
        Member member = this.memberService.getUser(principal.getName());

        if(bindingResult.hasErrors()){
            model.addAttribute("board",board);
            return "board/detail";
        }

        this.commentService.create(board, commentForm.getContent(),member);
        return String.format("redirect:/board/detail/%s",id);
    }



    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);

        if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }

        commentForm.setContent(comment.getContent());
        return "board/comment_form";
    }



    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Comment comment = this.commentService.getAnswer(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/question/detail/%s", comment.getBoard().getId());
    }
}