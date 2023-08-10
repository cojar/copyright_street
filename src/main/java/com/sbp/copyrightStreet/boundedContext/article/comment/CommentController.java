package com.sbp.copyrightStreet.boundedContext.article.comment;

import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.article.borad.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
