package com.sbp.copyrightStreet.boundedContext.article.borad;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

//    private final BoardRepository boardRepository;
    private final BoardService boardService;
//    @GetMapping("/list")
//    public String boardList(Model model){
//        List<Board> boardList = this.boardRepository.findAll();
//        model.addAttribute("boardList", boardList);
//        return "board_list";
//    }
    @GetMapping("/create")
    public String boardCreate(){
        return "board_form";
    }

    @GetMapping("/list")
    public String boardList(){
        return "board_list";
    }
    @PostMapping("/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "board_form";
        }
        this.boardService.create(boardForm.getTitle(), boardForm.getContent());
        return "redirect:/board/;ist";
    }
}
