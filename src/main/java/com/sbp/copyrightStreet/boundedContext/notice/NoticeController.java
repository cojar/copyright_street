package com.sbp.copyrightStreet.boundedContext.notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Notice> noticeList = this.noticeService.List();
        model.addAttribute("noticeList", noticeList);
        return "notice/notice_list";
    }

    @GetMapping("/create")
    public String Create() {
        return "notice/notice_form";
    }

    @PostMapping("/create")
    public String Create(@RequestParam String title, @RequestParam String content) {
        this.noticeService.Create(title, content);
        return "redirect:/notice/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Notice notice = this.noticeService.Detail(id);
        model.addAttribute("notice", notice);
        return "notice/notice_detail";
    }

    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Integer id, NoticeForm noticeForm ) {
        Notice notice = this.noticeService.Detail(id);
        model.addAttribute("notice", notice);
        return "notice/notice_modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, @Valid NoticeForm noticeForm, BindingResult bindingResult) {
        Notice notice = this.noticeService.Detail(id);
        if (bindingResult.hasErrors()) {
            return "notice/notice_modify";
        }
        this.noticeService.Modify(notice, noticeForm.getTitle(), noticeForm.getContent());

        return String.format("redirect:/notice/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id") Integer id) {
        Notice notice = this.noticeService.Detail(id);
        this.noticeService.Delete(notice);
        return "redirect:/notice/list";
    }
}
