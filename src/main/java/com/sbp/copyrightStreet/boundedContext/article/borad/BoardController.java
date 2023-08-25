package com.sbp.copyrightStreet.boundedContext.article.borad;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    @GetMapping("/list")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
        return "board/list";
    }



    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String boardCreate(){
        return "board/form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String boardCreate(@Valid BoardForm boardForm, @RequestParam String title, @RequestParam String content, @RequestParam String category, Principal principal
    ,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        Member author = this.memberService.getUser(principal.getName());

        this.boardService.create(title, content, category,author);

        return "redirect:/board/list";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(Model model, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);

        if(!board.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        boardForm.setCategory(board.getCategory());
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
        if(!board.getAuthor().getUsername().equals(principal.getName())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        this.boardService.modify(board, boardForm.getTitle(), boardForm.getContent(), boardForm.getCategory());
        return String.format("redirect:/board/detail/%s", id);
    }



    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);

        if(!board.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.boardService.delete(board);
        return "redirect:/board/list";
    }

}
