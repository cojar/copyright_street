package com.sbp.copyrightStreet.boundedContext.article.borad;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    @GetMapping("/list")
    public String boardList(Model model){
        List<Board> boardList = this.boardRepository.findAll();
        model.addAttribute("boardList", boardList);
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
    public String boardDetail(Model model, @PathVariable("id") Long id) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

//    @PostMapping("/create")
//    public String create(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
//        SiteUser siteUser = this.userService.getUser(principal.getName());
//        this.boardService.create(boardForm.getTitle(), boardForm.getContent());
//        return "redirect:/board/list";
//    }

}
