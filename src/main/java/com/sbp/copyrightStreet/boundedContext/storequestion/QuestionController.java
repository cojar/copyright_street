package com.sbp.copyrightStreet.boundedContext.storequestion;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import com.sbp.copyrightStreet.boundedContext.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private  final QuestionService questionService;
    private  final ProductService productService;
    private  final MemberService memberService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable Long id, Principal principal, @RequestParam String content) {
        Product product = this.productService.getProduct(id);
        Member member = this.memberService.findByUserName(principal.getName());
        this.questionService.create(product, member, content);
        return String.format("redirect:/product/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Model model, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없다.");
        }
        model.addAttribute("question", question);
        return "question/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyContent(@PathVariable Long id, @RequestParam String content, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없다.");
        }
        this.questionService.modify(question, content);
        long productId = question.getProduct().getId();
        return String.format("redirect:/product/detail/%s", productId);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id, Principal principal){
        Question question = this.questionService.getQuestion(id);
        if (!question.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없다.");
        }
        this.questionService.delete(question);
        long productId = question.getProduct().getId();
        return String.format("redirect:/product/detail/%s", productId);
    }
}
