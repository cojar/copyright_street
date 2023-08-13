package com.sbp.copyrightStreet.boundedContext.article.borad;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
        return "board/list";
    }



    @GetMapping("/create")
    public String boardCreate(){
        return "board/form";
    }

    @PostMapping("/create")
    public String boardCreate(@RequestParam String title, @RequestParam String content, @RequestParam String category){
        this.boardService.create(title, content, category);

        return "redirect:/board/list";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(Model model, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/modify/{id}")
    public String questionModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);

        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        return "board/form";
    }


    @PostMapping("/modify/{id}")
    public String questionModify(@Valid BoardForm boardForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        Board board = this.boardService.getBoard(id);

        this.boardService.modify(board, boardForm.getTitle(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", id);
    }



    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);

        this.boardService.delete(board);
        return "redirect:/board/list";
    }

}
