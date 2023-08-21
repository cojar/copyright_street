package com.sbp.copyrightStreet.boundedContext.article.borad;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final MemberService memberService;

    @GetMapping("/list")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page){
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
        return "board/list";
    }



    @GetMapping("/create")
    public String boardCreate(){
        return "board/form";
    }

//    @PostMapping("/create")
//    public String boardCreate(@RequestParam String title, @RequestParam String content, @RequestParam String category){
//        this.boardService.create(title, content, category);
//
//        return "redirect:/board/list";
//    }

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/create")
//    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
//        Member member = this.memberService.getUserByLoginId(principal.getName());
//        this.boardService.create(boardForm.getTitle(), boardForm.getContent(), member.getLoginId());
//        return "redirect:/board/list";
//    }
    @PostMapping("/create")
    public String boardCreate(@RequestParam String title, @RequestParam String content, @RequestParam String category, Principal principal){
        this.boardService.create(title, content, category);
        Member member = this.memberService.getUserByLoginId(principal.getName());
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



//    @GetMapping("/delete/{id}")
//    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
//        Board board = this.boardService.getBoard(id);
//
//        this.boardService.delete(board);
//        return "redirect:/board/list";
//    }


    @GetMapping("/delete/{id}")
    public String questionDelete2(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);

        this.boardService.delete(board);
        return "redirect:/board/detail/%s";
    }
}
