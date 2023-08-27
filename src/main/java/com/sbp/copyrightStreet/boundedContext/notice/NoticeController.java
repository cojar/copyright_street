package com.sbp.copyrightStreet.boundedContext.notice;

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
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Notice> paging = this.noticeService.getList(page);
        model.addAttribute("paging", paging);
        return "notice/notice_list";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String Create(NoticeForm noticeForm) {
        return "notice/notice_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String Create(@Valid NoticeForm noticeForm,
                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Member member = this.memberService.getUser(principal.getName());
        this.noticeService.Create(noticeForm.getTitle(), noticeForm.getContent(), member);
        return "redirect:/notice/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Notice notice = this.noticeService.Detail(id);
        model.addAttribute("notice", notice);

        Notice previousNotice = this.noticeService.PreviousNotice(id);// 이전글
        model.addAttribute("previousNotice", previousNotice);
        Notice nextNotice = this.noticeService.NextNotice(id);//다음글
        model.addAttribute("nextNotice", nextNotice);
        return "notice/notice_detail";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(Model model, @PathVariable("id") Integer id, NoticeForm noticeForm, Principal principal) {
        Notice notice = this.noticeService.Detail(id);
        if (!notice.getAdmin().getUsername().equals(principal.getName())) {
            model.addAttribute("errorMessage", "수정권한이 없습니다.");
        }
        noticeForm.setTitle(notice.getTitle());
        noticeForm.setContent(notice.getContent());
        return "notice/notice_modify";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(Model model, @Valid NoticeForm noticeForm, BindingResult bindingResult,
                         Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Notice notice = this.noticeService.Detail(id);
        if (!notice.getAdmin().getUsername().equals(principal.getName())) {
            model.addAttribute("errorMessage", "수정권한이 없습니다.");
        }
        this.noticeService.Modify(notice, noticeForm.getTitle(), noticeForm.getContent());
        return String.format("redirect:/notice/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String Delete(Model model, @PathVariable("id") Integer id, Principal principal) {
        Notice notice = this.noticeService.Detail(id);
        if (!notice.getAdmin().getUsername().equals(principal.getName())) {
            model.addAttribute("errorMessage", "삭제권한이 없습니다.");
        }
        this.noticeService.Delete(notice);
        return "redirect:/notice/list";
    }
}
