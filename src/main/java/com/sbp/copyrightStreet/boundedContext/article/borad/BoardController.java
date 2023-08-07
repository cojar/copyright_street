package com.sbp.copyrightStreet.boundedContext.article.borad;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<com.ll.exam.project.borad.Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
//        List<Board> boardList = this.boardService.getList();
//        model.addAttribute("boardList", boardList);
        return "board_list";
    }



    @GetMapping("/create")
    public String boardCreate(){
        return "board_form";
    }

    @PostMapping("/create")
    public String boardCreate(@RequestParam String title, @RequestParam String content){
        this.boardService.create(title, content);

        return "redirect:/board/list";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(Model model, @PathVariable("id") Integer id) {
        com.ll.exam.project.borad.Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }



}
