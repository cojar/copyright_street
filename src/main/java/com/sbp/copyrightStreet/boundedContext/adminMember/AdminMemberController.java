package com.sbp.copyrightStreet.boundedContext.adminMember;



import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/adm")
@Controller
public class AdminMemberController {
    private final MemberService memberService;

    @GetMapping("/memberList")
    public String list(Model model) {
        List<Member> memberList = this.memberService.getAll();
        model.addAttribute("memberList", memberList);

        return "adm/member/list";
    }
    @GetMapping("/memberDetail/{id}")
    public String detail(){

        return "adm/member/detail";
    }

    @GetMapping("/member/delete")
    @ResponseBody
    public List<Member> delete(@RequestParam(value = "userIds[]") List<Integer> userIds){
        this.memberService.delete(userIds);
        List<Member> memberList = this.memberService.getList();
        return memberList;
    }
}
